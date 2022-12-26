/**
 * 
 */
package org.teapotech.blockly.workspace.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.teapotech.blockly.block.def.event.HandleEventBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.block.execute.BlockExecutionContext;
import org.teapotech.blockly.block.execute.BlockExecutionHelper;
import org.teapotech.blockly.block.execute.BlockExecutionProgress;
import org.teapotech.blockly.block.execute.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.block.execute.WorkspaceExecution;
import org.teapotech.blockly.block.execute.WorkspaceExecution.Status;
import org.teapotech.blockly.block.start_stop.StartBlock;
import org.teapotech.blockly.exception.ExitBlockExecutionException;
import org.teapotech.blockly.execute.event.EventDispatcher;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Variable;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.model.Workspace.Blocks;
import org.teapotech.blockly.user.UserDelegate;
import org.teapotech.blockly.workspace.event.WorkspaceEvent;

/**
 * @author jiangl
 *
 */
public class WorkspaceExecutor {

    private static final int WORKSPACE_EXECUTION_TIMEOUT_SECONDS = 10 * 60; // 10 minutes

    private final BlockExecutionContext context;
    private final Workspace workspace;
    private final ThreadGroup threadGroup;
    private BlockExecutionMonitoringThread monitoringThread;
    private final WorkspaceExecution workspaceExecution;
    private int executionTimeout = WORKSPACE_EXECUTION_TIMEOUT_SECONDS;

    public WorkspaceExecutor(Workspace workspace, BlockExecutionContext context) {
        this.context = context;
        this.workspace = workspace;
        this.threadGroup = new ThreadGroup("wexec-" + context.getWorkspaceId() + "-" + context.getInstanceId());
        context.getLogger().info("Created workspace executor. ID: {}-{}", context.getWorkspaceId(),
                context.getInstanceId());
        workspaceExecution = new WorkspaceExecution(context.getInstanceId(), context.getWorkspaceId(),
                context.getExecutedBy());
    }

    public BlockExecutionContext getBlockExecutionContext() {
        return context;
    }

    public WorkspaceExecution getWorkspaceExecution() {
        return workspaceExecution;
    }

    public void setExecutionTimeout(int executionTimeout) {
        this.executionTimeout = executionTimeout;
        this.context.getLogger().info("set execution timeout={} seconds.", this.executionTimeout);
    }

    public int getExecutionTimeout() {
        return executionTimeout;
    }

    public void addBreakpoint(String blockId) {
        this.context.addBreakPoint(blockId);
    }

    public void setDebugMode(boolean debugMode) {
        this.context.setDebugMode(debugMode);
    }

    public void resumeExecution() {
        AbstractBlockExecutor blockExecutor = context.getCurrentBlockExecutor();
        if (blockExecutor != null) {
            blockExecutor.resumeExecution();
        }
    }

    public void startExecute() {

        workspaceExecution.setStartTime(new Date());
        workspaceExecution.setStatus(Status.Running);

        context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(
                new WorkspaceEvent(context.getWorkspaceId(), context.getInstanceId(), Status.Running));

        List<Variable> variables = workspace.getVariables();
        if (variables != null) {
            variables.stream().forEach(v -> {
                context.setVariableValue("_var_" + v.id(), Variable.NULL);
                context.getLogger().debug("Added variable: {}", v.name());
            });
        }

        Blocks startBlocks = workspace.getBlocks();

        BlockExecutionThread entryPointThread = null;

        List<String> eventBlockThreadNames = new ArrayList<String>();
        for (Block block : startBlocks.getBlocks()) {

            if (block.getType().equals(StartBlock.BLOCK_TYPE)) {
                entryPointThread = new BlockExecutionThread(block, this.threadGroup);
                context.getBlockExecutionProgress().put(entryPointThread.getName(),
                        new BlockExecutionProgress(entryPointThread.getName()));

            } else if (block.getType().equals(HandleEventBlock.BLOCK_TYPE)) {
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
            entryPointThread.monitoringThread = monitoringThread;
            entryPointThread.start();
            startMonitoring();

        } else {
            context.getLogger().warn("Cannot find start block");
        }
    }

    public void startMonitoring() {
        monitoringThread = new BlockExecutionMonitoringThread(
                "monitoring-" + context.getWorkspaceId() + "-" + context.getInstanceId());
        monitoringThread.setPriority(Thread.MAX_PRIORITY);
        monitoringThread.start();
    }

    public void waitFor() {
        try {
            monitoringThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void waitFor(long milliSec) {
        if (monitoringThread == null) {
            context.getLogger().warn("Monitoring thread didn't start.");
            return;
        }
        try {
            monitoringThread.join(milliSec);
        } catch (InterruptedException e) {
        }
    }

    public void stopExecution() {
        this.context.setStopped(true);
        workspaceExecution.setStatus(Status.Stopping);
        this.threadGroup.interrupt();
    }

    public void stopExecution(UserDelegate stoppedBy) {
        workspaceExecution.setEndBy(stoppedBy);
        this.stopExecution();
    }

    private class BlockExecutionThread extends Thread {

        private final Block startBlock;
        private BlockExecutionMonitoringThread monitoringThread;

        public BlockExecutionThread(Block startBlock, ThreadGroup threadGroup) {
            super(threadGroup, "bet." + startBlock.getId());
            this.startBlock = startBlock;
            context.getLogger().info("Created block execution thread. Block ID: {}, Type: {}, Group: {}",
                    startBlock.getId(), startBlock.getType(), threadGroup.getName());
        }

        @Override
        public void run() {
            context.getLogger().info("Thread {} start running.", getName());
            try {
                BlockExecutionHelper.execute(startBlock, null, context);
            } catch (ExitBlockExecutionException e) {
                workspaceExecution.setStatus(Status.Succeeded);
                stopExecution();
            } catch (Exception e) {
                context.getLogger().error(e.getMessage(), e);
                workspaceExecution.setStatus(Status.Failed);
                workspaceExecution.setMessage(e.getMessage());
                stopExecution();
            }

            if (monitoringThread != null) {
                monitoringThread.interrupt();
            }

            String name = Thread.currentThread().getName();
            BlockExecutionProgress beg = context.getBlockExecutionProgress().get(name);
            if (beg == null) {
                context.getLogger().error("Cannot find block execution thread by name: {}", name);
            }
            context.getLogger().info("Thread {} stopped.", getName());
            context.getLogger().info("Block execution thread exited. Group: {}, Active: {}", threadGroup.getName(),
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
                    context.getLogger().warn("The workspace executor {} is not running.",
                            context.getWorkspaceId() + "-" + context.getInstanceId());
                    break;
                }
                for (Thread t : blockExecutionThreads) {
                    if (t == null || t.getName().equals(this.getName())) {
                        continue;
                    }
                    running = running || t.isAlive();

                    Date startTime = workspaceExecution.getStartTime();
                    double duration = (System.currentTimeMillis() - startTime.getTime()) / 1000.0;
                    context.getLogger().debug("Duration: {} seconds", duration);
                    if (duration > executionTimeout) {
                        context.getLogger().warn(
                                "Workspace execution time out, instance ID: {}, workspace ID: {}. Duration: {}",
                                context.getInstanceId(), workspace.getId(), duration);
                        workspaceExecution.setMessage("Workspace execution time out");
                        workspaceExecution.setStatus(Status.Timeout);
                        stopExecution();
                    }
                }
                if (context.getCurrentBlockExecutor() != null) {
                    workspaceExecution.setCurrentBlockId(context.getCurrentBlockExecutor().getBlockId());
                    if (context.getCurrentBlockExecutor().isPaused()) {
                        workspaceExecution.setStatus(Status.Paused);
                    } else if (workspaceExecution.getStatus() == Status.Paused) {
                        workspaceExecution.setStatus(Status.Running);
                    }
                } else {
                    workspaceExecution.setCurrentBlockId(null);
                }

                if (!running) {
                    break;
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    context.getLogger().warn("Block monitoring thread is interrupted.");
                    break;
                }
            }
            context.getLogger().info("All block execution threads are stopped. Ouput dir: {}",
                    context.getWorkingDir().getAbsolutePath());
            workspaceExecution.setEndTime(new Date());
            if (context.isStopped()) {
                if (workspaceExecution.getStatus() == Status.Stopping) {
                    workspaceExecution.setStatus(Status.Stopped);
                }
            } else {
                workspaceExecution.setStatus(Status.Succeeded);
            }
            context.getContextObject(EventDispatcher.class).dispatchWorkspaceEvent(new WorkspaceEvent(
                    context.getWorkspaceId(), context.getInstanceId(), workspaceExecution.getStatus()));
            if (!context.isStopped()) {
                context.setStopped(true);
            }
            context.destroy();
        }
    }
}
