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
import org.teapotech.blockly.model.Workspace;
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
    // Accessible only by the same thread where it gets defined. Reference only by name:
    private final ThreadLocal<Map<String, Object>> localVariables = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };
    // Accessible within the workspace. It's defined by user.
    private final Map<String, Object> workspaceVariables = new ConcurrentHashMap<>();
    private final Map<String, BlockExecutionProgress> executionThreadInfo = new HashMap<>();

    private final Map<String, String> variableDefs = new ConcurrentHashMap<>();

    public DefaultBlockExecutionContext(Workspace workspace, long instanceId, UserDelegate executedBy, File workingDir,
                                        BlockExecutorFactory[] blockExecutorFactories) {
        this.executedBy = executedBy;
        this.blockExecutorFactories = blockExecutorFactories;
        this.workspaceId = workspace.getId();
        this.instanceId = instanceId;
        this.workingDir = workingDir;
        this.workspaceLogger = WorkspaceLoggerFactory.createLogger(workspaceId, instanceId, "DEBUG", workingDir);
        if(workspace.getVariables()!=null) {
            workspace.getVariables().forEach(v -> variableDefs.put(v.id(), v.name()));
        }
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
    public void setLocalVariableValue(String id, Object value) {
        localVariables.get().put(id, value);

    }

    public Collection<String> getLocalVariableIds() {
        return localVariables.get().keySet();
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
    public String getWorkspaceVariableName(String id) {
        return variableDefs.get(id);
    }

    public Map<String, Object> getWorkspaceVariableValueMap(){
        Map<String, Object> result = new HashMap<>();
        this.workspaceVariables.forEach((key, value) -> {
            if(key.startsWith("_var_")) {
                key = key.substring(5);
            }
            result.put(variableDefs.get(key), value);
        });
        this.localVariables.get().forEach((key, value) -> {
            if(key.startsWith("_var_")) {
                key = key.substring(5);
            }
            result.put(variableDefs.get(key), value);
        });
        return result;
    }

    @Override
    public Object getWorkspaceVariableValue(String id) {
        return workspaceVariables.get(id);
    }

    @Override
    public void setWorkspaceVariableValue(String id, Object value) {
        workspaceVariables.put(id, value);
    }

    @Override
    public Collection<String> getWorkspaceVariableIds() {
        return workspaceVariables.keySet();
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
    public synchronized AbstractBlockExecutor getCurrentBlockExecutor() {
        return currentBlockExecutor;
    }

    @Override
    public synchronized void setCurrentBlockExecutor(AbstractBlockExecutor blockExecutor) {
        this.currentBlockExecutor = blockExecutor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <T> T getContextObject(Class<T> objectType) {
        T object = (T) contextObjects.get(objectType);
        if (object == null) {
            throw new IllegalArgumentException("Cannot find context object of type " + objectType);
        }
        return object;
    }

    public synchronized <T> void setContextObject(Class<T> objectType, T object) {
        if (contextObjects.containsKey(objectType)) {
            LOG.warn("Object of type {} already exists in the context. Reset will be ignored.", objectType);
            return;
        }
        contextObjects.put(objectType, object);
        LOG.debug("Set context object {}={}", objectType, object);
    }

}
