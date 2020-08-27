package org.teapotech.blockly.block.def;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.annotation.BlockDef;
import org.teapotech.blockly.block.executor.AbstractBlockExecutor;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Workspace;
import org.teapotech.blockly.util.BlockXmlUtils;
import org.teapotech.blockly.util.JSONUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class BlockExecutorRegistry {

	private static Logger LOG = LoggerFactory.getLogger(BlockExecutorRegistry.class);
	private final Map<String, JsonNode> defaultCategories = new HashMap<>();
	private final Map<BlockDefinition, Class<? extends AbstractBlockExecutor>> blockExecutors = new HashMap<>();

	public BlockExecutorRegistry() throws Exception {
		loadBlockExecutors();
	}

	public BlockDefinition getBlockDefinition(String blockType) {
		return blockExecutors.keySet().stream().filter(bdef -> bdef.getBlockType().equals(blockType)).findAny()
				.orElse(null);
	}

	public Class<? extends AbstractBlockExecutor> getBlockExecutorClassByBlockType(String blockType) {

		Optional<Entry<BlockDefinition, Class<? extends AbstractBlockExecutor>>> op = blockExecutors.entrySet().stream()
				.filter(e -> e.getKey().getBlockType().equals(blockType)).findAny();
		if (op.isPresent()) {
			return op.get().getValue();
		}
		return null;
	}

	public void loadBlockExecutors() throws Exception {

		loadDefaultCategories();

		Reflections reflections = new Reflections("org.teapotech");

		Set<Class<? extends AbstractBlockExecutor>> executorClasses = reflections
				.getSubTypesOf(AbstractBlockExecutor.class);

		for (Class<? extends AbstractBlockExecutor> c : executorClasses) {
			BlockDef a = c.getDeclaredAnnotation(BlockDef.class);
			if (a == null) {
				continue;
			}
			String blockType = a.blockType();
			if (!StringUtils.isBlank(blockType)) {
				String category = a.category();
				DefaultBlockDefinition bdef = new DefaultBlockDefinition(blockType, category);
				blockExecutors.put(bdef, c);
				LOG.info("Registered block, Type: {}, Executor class: {}", blockType, c.getName());
			} else {
				Class<? extends BlockDefinition> blockDefClass = a.value();
				if (Modifier.isAbstract(c.getModifiers())) {
					continue;
				}
				try {
					BlockDefinition blockDef = blockDefClass.getConstructor(null).newInstance(null);
					blockExecutors.put(blockDef, c);
					LOG.info("Registered block, Type: {}, Executor class: {}", blockDef.getBlockType(), c.getName());
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
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

	public Workspace getToolboxConfiguration() {
		Workspace toolbox = new Workspace();
		blockExecutors.keySet().stream().forEach(bdef -> {
			String category = bdef.getCategory();
			Category cat = findCategory(toolbox, category);
			if (cat != null) {
				try {
					Block tb = getToolboxConfig(bdef.getBlockType());
					if (tb == null) {
						tb = new Block();
						tb.setType(bdef.getBlockType());
					}
					if (cat.getBlocks() == null) {
						cat.setBlocks(new ArrayList<>());
					}
					cat.getBlocks().add(tb);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		});
		return toolbox;
	}

	private void loadDefaultCategories() throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream("category-definition.json");
		ArrayNode an = (ArrayNode) JSONUtils.getObject(in);
		Iterator<JsonNode> it = an.iterator();
		while (it.hasNext()) {
			JsonNode n = it.next();
			String id = n.get("id").asText();
			defaultCategories.put(id, n);
		}
	}

	public Category findCategory(Workspace workspace, String idChain) {
		String[] cc = idChain.split("\\s*/\\s*");
		Category cat = null;
		List<Category> cl = workspace.getCategories();
		if (cl == null) {
			cl = new ArrayList<>();
			workspace.setCategories(cl);
		}
		for (String cid : cc) {
			Optional<Category> op = cl.stream().filter(c -> c.getId().equalsIgnoreCase(cid)).findFirst();
			if (op.isPresent()) {
				cat = op.get();
				cl = cat.getCategories();
			} else {
				JsonNode n = this.defaultCategories.get(cid);
				if (n == null) {
					LOG.error("Cannot find category by ID: {}", cid);
					continue;
				}
				cat = new Category();
				cat.setId(cid);
				cat.setName(n.get("name").asText());
				cat.setCategorystyle(n.get("style").asText());
				cl.add(cat);
				cl = cat.getCategories();
			}
			if (cl == null) {
				cl = new ArrayList<>();
				cat.setCategories(cl);
			}
		}
		return cat;
	}

	public List<CustomBlockTemplate> getCustomBlockDefinitions() {
		List<CustomBlockTemplate> result = new ArrayList<>();
		blockExecutors.keySet().stream().filter(bdef -> bdef instanceof CustomBlockDefinition).forEach(bdef -> {
			CustomBlockDefinition cbDef = (CustomBlockDefinition) bdef;
			try {
				String blockTemplate = cbDef.getBlockDefinitionTemplate();
				if (StringUtils.isBlank(blockTemplate)) {
					return;
				}
				JsonNode json = JSONUtils.getObject(blockTemplate);
				result.add(new CustomBlockTemplate(cbDef.getBlockType(), json));
			} catch (Exception e) {
				LOG.error("Invalid block definition. Ignore it. \n{}", e.getMessage());
			}
		});

		return result;
	}
}
