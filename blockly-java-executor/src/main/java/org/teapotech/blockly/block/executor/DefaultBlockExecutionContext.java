/**
 * 
 */
package org.teapotech.blockly.block.executor;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.executor.event.EventDispatcher;
import org.teapotech.blockly.execution.provider.FileStorageProvider;
import org.teapotech.blockly.execution.provider.KeyValueStorageProvider;

/**
 * @author jiangl
 *
 */
public class DefaultBlockExecutionContext implements BlockExecutionContext {

	private static Logger LOG = LoggerFactory.getLogger(DefaultBlockExecutionContext.class);

	private final BlockExecutorFactory blockExecutorFactory;
	private final EventDispatcher eventDispatcher;
	private final KeyValueStorageProvider kvStorageProvider;
	private final FileStorageProvider fileStorageProvider;
	private final String workspaceId;
	private final long instanceId;
	private final File workingDir;

	private boolean stopped;
	private ThreadLocal<Map<String, Object>> localVariables = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>();
		}
	};
	private final Map<String, BlockExecutionProgress> executionThreadInfo = new HashMap<>();

	public DefaultBlockExecutionContext(String workspaceId, long instanceId, File workingDir,
			BlockExecutorFactory blockExecutorFactory, KeyValueStorageProvider kvStorageProvider,
			FileStorageProvider fileStorageProvider, EventDispatcher eventDispatcher) {
		this.blockExecutorFactory = blockExecutorFactory;
		this.eventDispatcher = eventDispatcher;
		this.kvStorageProvider = kvStorageProvider;
		this.fileStorageProvider = fileStorageProvider;
		this.workspaceId = workspaceId;
		this.instanceId = instanceId;
		this.workingDir = workingDir;
	}

	@Override
	public BlockExecutorFactory getBlockExecutorFactory() {
		return this.blockExecutorFactory;
	}

	@Override
	public String getWorkspaceId() {
		return workspaceId;
	}

	@Override
	public void destroy() {
	}

	@Override
	public boolean isStopped() {
		return this.stopped;
	}

	@Override
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	@Override
	public Logger getLogger() {
		return LOG;
	}

	@Override
	public void setLocalVariable(String id, Object value) {
		localVariables.get().put(id, value);

	}

	@Override
	public Object getLocalVariable(String id) {
		return localVariables.get().get(id);
	}

	@Override
	public Map<String, BlockExecutionProgress> getBlockExecutionProgress() {
		return this.executionThreadInfo;
	}

	@Override
	public Object getVariable(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariable(String id, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> getAllVariableNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDispatcher getEventDispatcher() {
		return this.eventDispatcher;
	}

	public FileStorageProvider getFileStorageProvider() {
		return fileStorageProvider;
	}

	public KeyValueStorageProvider getKvStorageProvider() {
		return kvStorageProvider;
	}

	@Override
	public long getInstanceId() {
		return this.instanceId;
	}

	@Override
	public File getWorkingDir() {
		return workingDir;
	}
}
