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

    Object getVariableValue(String key);

    void setVariableValue(String key, Object value);

    void setLocalVariableValue(String key, Object value);

    Object getLocalVariableValue(String key);

    Collection<String> getAllVariableNames();

    void destroy();

    boolean isStopped();

    void setStopped(boolean stopped);

    Logger getLogger();

    Map<String, BlockExecutionProgress> getBlockExecutionProgress();

    UserDelegate getExecutedBy();

    Set<String> getBreakPoints();

    boolean isDebugMode();

    boolean shouldPause(String blockId);

    AbstractBlockExecutor getCurrentBlockExecutor();

    void setCurrentBlockExecutor(AbstractBlockExecutor blockExecutor);

    <T> T getContextObject(Class<T> objectType);
}