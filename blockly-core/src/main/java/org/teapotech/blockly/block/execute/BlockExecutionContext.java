/**
 * 
 */
package org.teapotech.blockly.block.execute;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.user.UserDelegate;

/**
 * @author jiangl
 *
 */
public interface BlockExecutionContext {

    String getWorkspaceId();

    long getInstanceId();

    File getWorkingDir();

    BlockExecutorFactory getBlockExecutorFactory(String blockType) throws BlockExecutorNotFoundException;

    Object getWorkspaceVariableValue(String id);

    String getWorkspaceVariableName(String id);

    void setWorkspaceVariableValue(String id, Object value);

    void setLocalVariableValue(String id, Object value);

    Object getLocalVariableValue(String id);

    Collection<String> getWorkspaceVariableIds();

    Collection<String> getLocalVariableIds();

    void destroy();

    boolean isStopped();

    void setStopped(boolean stopped);

    Logger getLogger();

    Map<String, BlockExecutionProgress> getBlockExecutionProgress();

    UserDelegate getExecutedBy();

    Set<String> getBreakPoints();

    void addBreakPoint(String blockId);

    boolean isDebugMode();

    void setDebugMode(boolean debug);

    boolean shouldPause(String blockId);

    AbstractBlockExecutor getCurrentBlockExecutor();

    void setCurrentBlockExecutor(AbstractBlockExecutor blockExecutor);

    <T> T getContextObject(Class<T> objectType);

    Map<String, Object> getWorkspaceVariableValueMap();
}