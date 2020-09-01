/**
 * 
 */
package org.teapotech.blockly.block.executor;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.teapotech.blockly.event.EventDispatcher;

/**
 * @author jiangl
 *
 */
public interface BlockExecutionContext {

	String getWorkspaceId();

	long getInstanceId();

	File getWorkingDir();

	BlockExecutorFactory getBlockExecutorFactory();

	Object getVariable(String id);

	void setVariable(String id, Object value);

	void setLocalVariable(String id, Object value);

	Object getLocalVariable(String id);

	Collection<String> getAllVariableNames();

	void destroy();

	boolean isStopped();

	void setStopped(boolean stopped);

	Logger getLogger();

	Map<String, BlockExecutionProgress> getBlockExecutionProgress();

	EventDispatcher getEventDispatcher();

	String getExecutedBy();
}