package org.teapotech.blockly.block.def;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.block.executor.BlockExecutorFactory;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.util.BlockXmlUtils;
import org.teapotech.blockly.util.JSONUtils;

import com.fasterxml.jackson.core.type.TypeReference;

public class BlockDefinitionObjectRegistry {

	private static Logger LOG = LoggerFactory.getLogger(BlockDefinitionObjectRegistry.class);
	private final Map<BlockDefinitionObject, Class<? extends AbstractBlockExecutor>> blockExecutors = new HashMap<>();
	private final Map<String, Class<? extends AbstractBlockExecutor>> blockTypeExecutors = new HashMap<>();
	private final Map<String, BlockDefinition> customBlockDefinitions = new HashMap<>();

	public BlockDefinition getCustomBlockDefinition(String blockType) {
		return customBlockDefinitions.get(blockType);
	}

	public List<BlockDefinitionObject> getBlockRegistries() {
		List<BlockDefinitionObject> result = new ArrayList<>();
		for (BlockDefinitionObject breg : this.blockExecutors.keySet()) {
			if (customBlockDefinitions.containsKey(breg.getType())) {
				BlockDefinition bdef = customBlockDefinitions.get(breg.getType());
				breg.setDefinition(bdef.getConfiguration());
			}
			result.add(breg);
		}
		return result;
	}

	public void loadBlockRegistries() {
		loadDefaultBlockDefinitions();
		loadCustomBlockDefinitions();
	}

	@SuppressWarnings("unchecked")
	private void loadDefaultBlockDefinitions() {
		List<BlockDefinitionObject> regs = null;
		try (InputStream in = BlockExecutorFactory.class.getClassLoader()
				.getResourceAsStream("basic-block-definitions.json");) {
			regs = JSONUtils.getObject(in, new TypeReference<List<BlockDefinitionObject>>() {
			});
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		if (regs == null) {
			return;
		}
		for (BlockDefinitionObject br : regs) {
			try {
				Class<? extends AbstractBlockExecutor> c = (Class<? extends AbstractBlockExecutor>) Class
						.forName(br.getExecutorClass());
				registerBlock(br, c);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
	}

	private void loadCustomBlockDefinitions() {

		Reflections reflections = new Reflections("org.teapotech");

		HashMap<Class<? extends BlockDefinition>, Class<? extends AbstractBlockExecutor>> cc = new HashMap<>();
		Set<Class<? extends AbstractBlockExecutor>> executorClasses = reflections
				.getSubTypesOf(AbstractBlockExecutor.class);
		for (Class<? extends AbstractBlockExecutor> c : executorClasses) {
			ForBlockDefinition a = c.getDeclaredAnnotation(ForBlockDefinition.class);
			if (a == null) {
				continue;
			}
			Class<? extends BlockDefinition> blockDefClass = a.value();
			cc.put(blockDefClass, c);
		}

		Set<Class<? extends BlockDefinition>> classes = reflections.getSubTypesOf(BlockDefinition.class);
		for (Class<? extends BlockDefinition> c : classes) {
			if (Modifier.isAbstract(c.getModifiers())) {
				continue;
			}
			Class<? extends AbstractBlockExecutor> executorClass = cc.get(c);
			if (executorClass == null) {
				LOG.error("Cannot find executor class for block {}", c);
				continue;
			}
			LOG.info("Found block definition class: {}", c);
			try {
				BlockDefinition blockDef = c.getConstructor(null).newInstance(null);
				customBlockDefinitions.put(blockDef.getBlockType(), blockDef);
				BlockDefinitionObject reg = new BlockDefinitionObject();
				reg.setDefinition(blockDef.getConfiguration());
				reg.setType(blockDef.getBlockType());
				reg.setCategory(blockDef.getCategory());
				blockTypeExecutors.put(blockDef.getBlockType(), executorClass);

				LOG.info("Registered custom block, Type: {}, Executor class: {}", reg.getType(),
						reg.getExecutorClass());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	public void registerBlock(BlockDefinitionObject reg, Class<? extends AbstractBlockExecutor> executorClass) {
		this.blockExecutors.put(reg, executorClass);
		LOG.info("Registered custom block, Type: {}, Executor class: {}", reg.getType(), reg.getExecutorClass());
	}

	public Block getToolboxConfig(String blockType) throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("toolbox-config/" + blockType + ".xml");
		if (in == null) {
			Block b = new Block();
			b.setType(blockType);
			return b;
		}
		return BlockXmlUtils.loadToolboxBlock(in);

	}

	public Class<? extends AbstractBlockExecutor> getBlockExecutorClassByBlockType(String blockType) {
		return blockTypeExecutors.get(blockType);
	}

}
