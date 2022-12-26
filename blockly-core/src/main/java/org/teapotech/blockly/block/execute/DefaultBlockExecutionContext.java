/**
 * 
 */
package org.teapotech.blockly.block.execute;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.user.UserDelegate;

/**
 * @author jiangl
 *
 */
public class DefaultBlockExecutionContext implements BlockExecutionContext {

    private static Logger LOG = LoggerFactory.getLogger(DefaultBlockExecutionContext.class);

    private final BlockExecutorFactory[] blockExecutorFactories;
    private final String workspaceId;
    private final long instanceId;
    private final File workingDir;
    private final UserDelegate executedBy;
    private final Set<String> breakpoints = new HashSet<>();
    private final Map<Class<?>, Object> contextObjects = new HashMap<>();
    private boolean debug = false;
    private AbstractBlockExecutor currentBlockExecutor;
    private final Logger workspaceLogger;

    private boolean stopped;
    private ThreadLocal<Map<String, Object>> localVariables = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };
    private Map<String, Object> globalVariables = new ConcurrentHashMap<>();
    private final Map<String, BlockExecutionProgress> executionThreadInfo = new HashMap<>();

    public DefaultBlockExecutionContext(String workspaceId, long instanceId, UserDelegate executedBy, File workingDir,
            BlockExecutorFactory[] blockExecutorFactories) {
        this.executedBy = executedBy;
        this.blockExecutorFactories = blockExecutorFactories;
        this.workspaceId = workspaceId;
        this.instanceId = instanceId;
        this.workingDir = workingDir;
        this.workspaceLogger = WorkspaceLoggerFactory.createLogger(workspaceId, instanceId, "DEBUG", workingDir);
    }

    @Override
    public BlockExecutorFactory getBlockExecutorFactory(String blockType) throws BlockExecutorNotFoundException {
        for (BlockExecutorFactory fac : this.blockExecutorFactories) {
            if (fac.getBlockDefinition(blockType) != null) {
                return fac;
            }
        }
        throw new BlockExecutorNotFoundException("No block executor for block, type: " + blockType);
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
    public synchronized void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    public Logger getLogger() {
        return this.workspaceLogger;
    }

    @Override
    public void setLocalVariableValue(String key, Object value) {
        localVariables.get().put(key, value);

    }

    @Override
    public Object getLocalVariableValue(String key) {
        return localVariables.get().get(key);
    }

    @Override
    public Map<String, BlockExecutionProgress> getBlockExecutionProgress() {
        return this.executionThreadInfo;
    }

    @Override
    public Object getVariableValue(String key) {
        return globalVariables.get(key);
    }

    @Override
    public void setVariableValue(String key, Object value) {
        globalVariables.put(key, value);
    }

    @Override
    public Collection<String> getAllVariableNames() {
        return globalVariables.keySet();
    }

    @Override
    public long getInstanceId() {
        return this.instanceId;
    }

    @Override
    public File getWorkingDir() {
        return workingDir;
    }

    @Override
    public UserDelegate getExecutedBy() {
        return this.executedBy;
    }

    @Override
    public Set<String> getBreakPoints() {
        return breakpoints;
    }

    @Override
    public boolean isDebugMode() {
        return debug;
    }

    @Override
    public void setDebugMode(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void addBreakPoint(String blockId) {
        this.breakpoints.add(blockId);
    }

    public void removeBreakPoint(String blockId) {
        this.breakpoints.remove(blockId);
    }

    @Override
    public boolean shouldPause(String blockId) {
        return isDebugMode() && breakpoints.contains(blockId);
    }

    @Override
    public AbstractBlockExecutor getCurrentBlockExecutor() {
        return currentBlockExecutor;
    }

    @Override
    public void setCurrentBlockExecutor(AbstractBlockExecutor blockExecutor) {
        this.currentBlockExecutor = blockExecutor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getContextObject(Class<T> objectType) {
        T object = (T) contextObjects.get(objectType);
        if (object == null) {
            throw new IllegalArgumentException("Cannot find context object of type " + objectType);
        }
        return object;
    }

    public <T> void setContextObject(Class<T> objectType, T object) {
        if (contextObjects.containsKey(objectType)) {
            LOG.warn("Object of type {} already exists in the context. Reset will be ignored.", objectType);
            return;
        }
        contextObjects.put(objectType, object);
        LOG.debug("Set context object {}={}", objectType, object);
    }

}
