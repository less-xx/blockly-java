/**
 * 
 */
package org.teapotech.blockly.exception;

/**
 * @author jiangl
 *
 */
public class ExitBlockExecutionException extends BlockExecutionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8214934865678276629L;

	public ExitBlockExecutionException() {
		super("Exit workspace execution");
	}

}
