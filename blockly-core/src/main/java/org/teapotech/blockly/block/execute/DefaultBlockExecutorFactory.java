/**
 * 
 */
package org.teapotech.blockly.block.execute;

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.execute.support.BlockEventListenerSupport;
import org.teapotech.blockly.block.execute.support.JsonHelperSupport;
import org.teapotech.blockly.exception.BlockExecutorNotFoundException;
import org.teapotech.blockly.exception.InvalidBlockExecutorException;
import org.teapotech.blockly.execute.event.BlockEventListenerFactory;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Shadow;
import org.teapotech.blockly.user.UserDelegate;
import org.teapotech.blockly.util.BlockRegistry;
import org.teapotech.blockly.util.JsonHelper;

/**
 * @author jiangl
 *
 */
public class DefaultBlockExecutorFactory implements BlockExecutorFactory {

    private static Logger LOG = LoggerFactory.getLogger(DefaultBlockExecutorFactory.class);

    private final BlockRegistry blockRegistry;
    private final BlockEventListenerFactory blockEventListenerFactory;
    private final JsonHelper jsonHelper;

    public static DefaultBlockExecutorFactory build(BlockRegistry blockDefinitionObjectRegistry,
            BlockEventListenerFactory blockEventListnerFactory, JsonHelper jsonHelper) {
        DefaultBlockExecutorFactory fac = new DefaultBlockExecutorFactory(blockDefinitionObjectRegistry,
                blockEventListnerFactory, jsonHelper);
        return fac;
    }

    private DefaultBlockExecutorFactory(BlockRegistry blockDefinitionObjectRegistry,
            BlockEventListenerFactory blockEventListnerFactory, JsonHelper jsonHelper) {
        this.blockRegistry = blockDefinitionObjectRegistry;
        this.blockEventListenerFactory = blockEventListnerFactory;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public AbstractBlockExecutor createBlockExecutor(String workspaceId, Block block, Shadow shadow,
            UserDelegate executor) throws BlockExecutorNotFoundException, InvalidBlockExecutorException {

        if (block == null && shadow == null) {
            throw new IllegalArgumentException("either block or shadow must not be null");
        }
        String type = block == null ? shadow.getType() : block.getType();
        Class<? extends AbstractBlockExecutor> c = blockRegistry.getBlockExecutorClassByBlockType(type);
        if (c == null) {
            throw new BlockExecutorNotFoundException("Block executor not register for type " + type);
        }
        try {

            AbstractBlockExecutor be = null;

            Constructor<? extends AbstractBlockExecutor> constructor = c.getConstructor(Block.class, Shadow.class);
            if (constructor == null) {
                throw new InvalidBlockExecutorException(
                        "The block executor's constructor should have argument of Block type");
            }

            be = constructor.newInstance(block, shadow);
            LOG.debug("Created executor for block, type: {}", type);
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
    public BlockDefinition getBlockDefinition(String blockType) {
        return blockRegistry.getBlockDefinition(blockType);
    }

}
