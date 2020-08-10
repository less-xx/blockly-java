/**
 * 
 */
package org.teapotech.blockly.block.executor.event;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.def.event.HandleEventBlock;
import org.teapotech.blockly.block.event.NamedBlockEvent;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutionContext;
import org.teapotech.blockly.block.executor.BlockExecutionProgress.BlockStatus;
import org.teapotech.blockly.event.BlockEventListener;
import org.teapotech.blockly.event.BlockEventListenerSupport;
import org.teapotech.blockly.exception.BlockExecutionException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Block.Next;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.blockly.model.Field;
import org.teapotech.blockly.util.BlockExecutorUtils;

/**
 * @author jiangl
 *
 */
@BlockDef(value = HandleEventBlock.class)
public class HandleEventBlockExecutor extends AbstractBlockExecutor implements BlockEventListenerSupport {

	private BlockEventListener blockEventListener;
	private final static int DEFAULT_TIMEOUT_SECONDS = 5;

	public HandleEventBlockExecutor(Block block) {
		super(block);
	}

	public HandleEventBlockExecutor(BlockValue blockValue) {
		super(blockValue);
	}

	@Override
	public void setBlockEventListener(BlockEventListener listener) {
		this.blockEventListener = listener;
	}

	@Override
	protected Object doExecute(BlockExecutionContext context) throws Exception {

		updateBlockStatus(context, BlockStatus.Initializing);

		String eventName = null;
		Field evtNameField = this.block.getFieldByName("event_name", null);
		if (evtNameField != null) {
			eventName = evtNameField.getValue();
		}
		if (StringUtils.isBlank(eventName)) {
			throw new BlockExecutionException("Missing event name");
		}
		String evtName = eventName.replace("\\s*", "_");
		String routingKey = "workspace." + context.getWorkspaceId() + "." + evtName;
		blockEventListener.initialize(routingKey);
		Logger LOG = context.getLogger();
		updateBlockStatus(context, BlockStatus.Running);
		try {
			while (!context.isStopped()) {

				NamedBlockEvent evt = null;
				try {
					evt = blockEventListener.receive(DEFAULT_TIMEOUT_SECONDS);
				} catch (InterruptedException ie) {
					LOG.debug("Thread interruptted.");
				}
				if (evt == null) {
					continue;
				}
				LOG.info("Received event: {}", evt);

				context.setLocalVariable(evt.getEventName(), evt);
				LOG.info("Set local variable: {}", evt.getEventName());

				Next next = this.block.getNext();
				if (next != null && next.getBlock() != null) {
					BlockExecutorUtils.execute(next.getBlock(), context);
				}
			}
			blockEventListener.destroy();
		} catch (AmqpException ie) {
			LOG.error(ie.getMessage());
		}
		return null;
	}

}
