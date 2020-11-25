/**
 * 
 */
package org.teapotech.blockly.block.executor;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.block.def.control.WaitSecondsBlock;
import org.teapotech.blockly.block.def.event.DispatchEventBlock;
import org.teapotech.blockly.block.def.event.EventWithParameterBlock;
import org.teapotech.blockly.block.def.event.GetEventParameterBlock;
import org.teapotech.blockly.block.def.event.HandleEventBlock;
import org.teapotech.blockly.block.def.file.WatchFileSystemBlock;
import org.teapotech.blockly.block.def.loop.ForEachInCollectionBlock;
import org.teapotech.blockly.block.def.math.MathArithmeticBlock;
import org.teapotech.blockly.block.def.object.GetObjectPropertyBlock;
import org.teapotech.blockly.block.def.object.TextToJsonObjectBlock;
import org.teapotech.blockly.block.def.start_stop.ExitBlock;
import org.teapotech.blockly.block.def.start_stop.StartBlock;
import org.teapotech.blockly.block.def.variable.GetLocalVariableBlock;
import org.teapotech.blockly.block.def.variable.SetLocalVariableBlock;
import org.teapotech.blockly.block.executor.support.BlockEventListenerSupport;
import org.teapotech.blockly.block.executor.support.JsonHelperSupport;
import org.teapotech.blockly.event.BlockEventListenerFactory;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.BlockValue;
import org.teapotech.user.UserDelegate;
import org.teapotech.util.JsonHelper;

/**
 * @author jiangl
 *
 */
public class DefaultBlockExecutorFactory implements BlockExecutorFactory {

	private static Logger LOG = LoggerFactory.getLogger(DefaultBlockExecutorFactory.class);
	public static List<String> SUPPORTED_BLOCK_TYPES = List.of(WaitSecondsBlock.BLOCK_TYPE,
			DispatchEventBlock.BLOCK_TYPE, EventWithParameterBlock.BLOCK_TYPE, GetEventParameterBlock.BLOCK_TYPE,
			HandleEventBlock.BLOCK_TYPE, WatchFileSystemBlock.BLOCK_TYPE, ForEachInCollectionBlock.BLOCK_TYPE,
			MathArithmeticBlock.BLOCK_TYPE, GetObjectPropertyBlock.BLOCK_TYPE, TextToJsonObjectBlock.BLOCK_TYPE,
			ExitBlock.BLOCK_TYPE, StartBlock.BLOCK_TYPE, GetLocalVariableBlock.BLOCK_TYPE,
			SetLocalVariableBlock.BLOCK_TYPE, BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_SET,
			BlockDefinition.INTERNAL_BLOCK_TYPE_VARIABLES_GET, BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT,
			BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT_PRINT, BlockDefinition.INTERNAL_BLOCK_TYPE_TEXT_JOIN,
			BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_ROUND, BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_RANDOM_INT,
			BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_NUMBER, BlockDefinition.INTERNAL_BLOCK_TYPE_MATH_CHANGE,
			BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_WHILE_UNTIL,
			BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_REPEAT_EXT,
			BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_FLOW_STATEMENTS,
			BlockDefinition.INTERNAL_BLOCK_TYPE_LOGIC_COMPARE, BlockDefinition.INTERNAL_BLOCK_TYPE_CONTROLS_IF);

	private final BlockExecutorRegistry blockDefinitionObjectRegistry;
	private final BlockEventListenerFactory blockEventListenerFactory;
	private final JsonHelper jsonHelper;

	public static DefaultBlockExecutorFactory build(BlockExecutorRegistry blockDefinitionObjectRegistry,
			BlockEventListenerFactory blockEventListnerFactory, JsonHelper jsonHelper) {
		DefaultBlockExecutorFactory fac = new DefaultBlockExecutorFactory(blockDefinitionObjectRegistry,
				blockEventListnerFactory, jsonHelper);
		return fac;
	}

	private DefaultBlockExecutorFactory(BlockExecutorRegistry blockDefinitionObjectRegistry,
			BlockEventListenerFactory blockEventListnerFactory, JsonHelper jsonHelper) {
		this.blockDefinitionObjectRegistry = blockDefinitionObjectRegistry;
		this.blockEventListenerFactory = blockEventListnerFactory;
		this.jsonHelper = jsonHelper;
	}

	@Override
	public AbstractBlockExecutor createBlockExecutor(String workspaceId, Block block, UserDelegate executor)
			throws BlockExecutorNotFoundException, InvalidBlockExecutorException {

		if (block == null) {
			throw new IllegalArgumentException("block should not be null");
		}

		Class<? extends AbstractBlockExecutor> c = blockDefinitionObjectRegistry
				.getBlockExecutorClassByBlockType(block.getType());
		if (c == null) {
			throw new BlockExecutorNotFoundException("Block executor not register for type " + block.getType());
		}
		try {

			AbstractBlockExecutor be = null;

			Constructor<? extends AbstractBlockExecutor> constructor = c.getConstructor(Block.class);
			if (constructor == null) {
				throw new InvalidBlockExecutorException(
						"The block executor's constructor should have argument of Block type");
			}

			be = constructor.newInstance(block);

			if (ClassUtils.isAssignable(c, BlockEventListenerSupport.class)) {
				((BlockEventListenerSupport) be).setBlockEventListener(
						blockEventListenerFactory.createBlockEventListener(workspaceId, block.getId()));
			}
			if (ClassUtils.isAssignable(c, JsonHelperSupport.class)) {
				((JsonHelperSupport) be).setJsonHelper(jsonHelper);
			}

			return be;

		} catch (Exception e) {
			throw new InvalidBlockExecutorException(e.getMessage(), e);
		}
	}

	@Override
	public AbstractBlockExecutor createBlockExecutor(String workspaceId, BlockValue blockValue, UserDelegate executor)
			throws InvalidBlockException, BlockExecutorNotFoundException, InvalidBlockExecutorException {
		String blockType = null;
		String id = null;
		if (blockValue.getBlock() != null) {
			blockType = blockValue.getBlock().getType();
			id = blockValue.getBlock().getId();
		} else if (blockValue.getShadow() != null) {
			blockType = blockValue.getShadow().getType();
			id = blockValue.getShadow().getId();
		} else {
			throw new InvalidBlockException(id, blockType,
					"Block value should have either block or shadow, name: " + blockValue.getName());
		}
		Class<? extends AbstractBlockExecutor> c = blockDefinitionObjectRegistry
				.getBlockExecutorClassByBlockType(blockType);
		if (c == null) {
			throw new BlockExecutorNotFoundException("Block executor not register for type " + blockType);
		}

		try {

			AbstractBlockExecutor be = null;

			Constructor<? extends AbstractBlockExecutor> constructor = c.getConstructor(BlockValue.class);
			if (constructor == null) {
				throw new InvalidBlockExecutorException(
						"The block executor's constructor should have argument of BlockValue type");
			}

			be = constructor.newInstance(blockValue);

			if (ClassUtils.isAssignable(c, BlockEventListenerSupport.class)) {
				((BlockEventListenerSupport) be)
						.setBlockEventListener(blockEventListenerFactory.createBlockEventListener(workspaceId, id));
			}
			if (ClassUtils.isAssignable(c, JsonHelperSupport.class)) {
				((JsonHelperSupport) be).setJsonHelper(jsonHelper);
			}

			return be;

		} catch (Exception e) {
			throw new InvalidBlockExecutorException(e.getMessage(), e);
		}
	}

	@Override
	public boolean supportBlockType(String blockType) {
		return SUPPORTED_BLOCK_TYPES.contains(blockType);
	}
}
