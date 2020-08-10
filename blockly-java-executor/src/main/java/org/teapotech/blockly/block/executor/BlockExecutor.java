/**
 * 
 */
package org.teapotech.blockly.block.executor;

import org.teapotech.blockly.exception.BlockExecutionException;

/**
 * @author jiangl
 *
 */
public interface BlockExecutor {


	String getBlockType();

	Object execute(BlockExecutionContext context) throws BlockExecutionException;
}
