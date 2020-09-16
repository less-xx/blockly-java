/**
 * 
 */
package org.teapotech.blockly.workspace.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.event.HandleEventBlock;
import org.teapotech.blockly.block.def.start_stop.StartBlock;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.block.executor.BlockExecutorFactory;
import org.teapotech.blockly.entity.WorkspaceExecution;
import org.teapotech.blockly.entity.WorkspaceExecution.Status;
import org.teapotech.blockly.event.EventDispatcher;
import org.teapotech.blockly.exception.ExitBlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Variable;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.util.BlockExecutorUtils;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public class WorkspaceExecutor {

	private static final Logger LOG = LoggerFactory.getLogger(WorkspaceExecutor.class);
	private static final int WORKSPACE_EXECUTION_TIMEOUT_SECONDS = 10 * 60; // 10 minutes

	private final BlockExecutionContext context;
	private final Workspace workspace;
	private final ThreadGroup threadGroup;
	private BlockExecutionMonitoringThread monitoringThread;
	private final WorkspaceExecution workspaceExecution = new WorkspaceExecution();
	private int executionTimeout = WORKSPACE_EXECUTION_TIMEOUT_SECONDS;

	public WorkspaceExecutor(Workspace workspace, BlockExecutionContext context) {
		this.context = context;
		this.workspace = workspace;
		this.threadGroup = new ThreadGroup("wexec-" + context.getWorkspaceId() + "-" + context.getInstanceId());
		this.threadGroup.setDaemon(true);
		LOG.info("Created workspace executor. ID: {}-{}", context.getWorkspaceId(), context.getInstanceId());
		workspaceExecution.setId(context.getInstanceId());
		workspaceExecution.setStatus(Status.Waiting);
		workspaceExecution.setWorkspaceId(workspace.getId());
		workspaceExecution.setStartBy(context.getExecutedBy());
		workspaceExecution.setTriggerTime(new Date());
	}

	public BlockExecutionContext getBlockExecutionContext() {
		return context;
	}

	public BlockExecutorFactory getBlockExecutorFactory() {
		return context.getBlockExecutorFactory();
	}

	public WorkspaceExecution getWorkspaceExecution() {
		return workspaceExecution;
	}

	public void setExecutionTimeout(int executionTimeout) {
		this.executionTimeout = executionTimeout;
	}

	public int getExecutionTimeout() {
		return executionTimeout;
	}

	public void resumeExecution() {
		AbstractBlockExecutor blockExecutor = context.getCurrentBlockExecutor();
		if (blockExecutor != null) {
			blockExecutor.resumeExecution();
		}
	}

	public void execute() {

		workspaceExecution.setStartTime(new Date());
		workspaceExecution.setStatus(Status.Running);

		context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(
				new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Running));

		List<Variable> variables = workspace.getVariables();
		if (variables != null) {
			variables.stream().forEach(v -> {
				context.setVariable(v.getValue(), "");
				LOG.debug("Added variable: {}", v.getValue());
			});
		}

		List<Block> startBlocks = workspace.getBlocks();

		BlockExecutionThread entryPointThread = null;

		List<String> eventBlockThreadNames = new ArrayList<String>();
		for (Block block : startBlocks) {

			if (block.getType().equals(StartBlock.TYPE)) {
				entryPointThread = new BlockExecutionThread(block, this.threadGroup);
				context.getBlockExecutionProgress().put(entryPointThread.getName(),
						new BlockExecutionProgress(entryPointThread.getName()));

			} else if (block.getType().equals(HandleEventBlock.TYPE)) {
				BlockExecutionThread bt = new BlockExecutionThread(block, this.threadGroup);
				context.getBlockExecutionProgress().put(bt.getName(), new BlockExecutionProgress(bt.getName()));
				bt.start();
				eventBlockThreadNames.add(bt.getName());
			}
		}

		if (entryPointThread != null) {
			boolean initialized = false;
			while (!initialized) {
				boolean b = true;
				for (String tn : eventBlockThreadNames) {
					BlockExecutionProgress beg = context.getBlockExecutionProgress().get(tn);
					if (beg != null) {
						b = b && (beg.getBlockStatus() == BlockStatus.Enter);
					}
				}
				initialized = b;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					break;
				}
			}

			entryPointThread.start();
			startMonitoring();
		} else {
			LOG.warn("Cannot find start block");
		}
	}

	public void startMonitoring() {
		monitoringThread = new BlockExecutionMonitoringThread(
				"monitoring-" + context.getWorkspaceId() + "-" + context.getInstanceId());
		monitoringThread.start();
	}

	public void waitFor() {
		try {
			monitoringThread.join();
		} catch (InterruptedException e) {

		}
	}

	public void waitFor(long milliSec) {
		try {
			monitoringThread.join(milliSec);
		} catch (InterruptedException e) {

		}
	}

	public void stopExecution() {
		workspaceExecution.setStatus(Status.Stopping);
		this.context.setStopped(true);
		this.threadGroup.interrupt();
	}

	public void stopExecution(String stoppedBy) {
		workspaceExecution.setEndBy(stoppedBy);
		this.stopExecution();
	}

	private class BlockExecutionThread extends Thread {

		private final Block startBlock;

		public BlockExecutionThread(Block startBlock, ThreadGroup threadGroup) {
			super(threadGroup, "bet." + startBlock.getId());
			this.startBlock = startBlock;
			LOG.info("Created block execution thread. Block ID: {}, Type: {}, Group: {}", startBlock.getId(),
					startBlock.getType(), threadGroup.getName());
		}

		@Override
		public void run() {
			context.getLogger().info("Thread {} start running.", getName());
			try {
				BlockExecutorUtils.execute(startBlock, context);
			} catch (ExitBlockExecutionException e) {
				stopExecution();
			} catch (Exception e) {
				context.getLogger().error(e.getMessage());
				LOG.error(e.getMessage(), e);
			}

			String name = Thread.currentThread().getName();
			BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
			if (beg == null) {
				context.getLogger().error("Cannot find block execution thread by name: {}", name);
			}
			context.getLogger().info("Thread {} stopped.", getName());
			LOG.info("Block execution thread exited. Group: {}, Active: {}", threadGroup.getName(),
					threadGroup.activeCount());
		}
	}

	private class BlockExecutionMonitoringThread extends Thread {

		public BlockExecutionMonitoringThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				boolean running = false;
				Thread[] blockExecutionThreads = new Thread[threadGroup.activeCount()];
				threadGroup.enumerate(blockExecutionThreads);
				if (blockExecutionThreads.length < 1) {
					LOG.warn("The workspace executor {} is not running.",
							context.getWorkspaceId() + "-" + context.getInstanceId());
					break;
				}
				for (Thread t : blockExecutionThreads) {
					if (t.getName().equals(this.getName())) {
						continue;
					}
					running = running || t.isAlive();

					Date startTime = workspaceExecution.getStartTime();
					long duration = (System.currentTimeMillis() - startTime.getTime()) / 1000;
					if (duration > executionTimeout) {
						LOG.warn("Workspace execution time out, instance ID: {}, workspace ID: {}. Duration: {}",
								context.getInstanceId(), workspace.getId(), duration);
						workspaceExecution.setMessage("Workspace execution time out");
						stopExecution();
					}
				}
				if (context.getCurrentBlockExecutor() != null) {
					workspaceExecution.setCurrentBlock(context.getCurrentBlockExecutor().getBlockId());
					if (context.getCurrentBlockExecutor().isPaused()) {
						workspaceExecution.setStatus(Status.Paused);
					}
				}

				if (!running) {
					break;
				}
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					LOG.warn("Block monitoring thread is interrupted.");
					break;
				}
			}
			LOG.info("All block execution threads are stopped. Ouput dir: {}",
					context.getWorkingDir().getAbsolutePath());
			workspaceExecution.setEndTime(new Date());
			workspaceExecution.setStatus(Status.Stopped);
			context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(
					new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Stopped));
			if (!context.isStopped()) {
				context.setStopped(true);
			}
			context.destroy();
		}
	}
}
