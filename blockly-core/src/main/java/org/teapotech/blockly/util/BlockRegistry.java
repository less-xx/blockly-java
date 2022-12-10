package org.teapotech.blockly.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.CustomBlockConfiguration;
import org.teapotech.blockly.block.def.CustomBlockDefinition;
import org.teapotech.blockly.block.def.DefaultBlockDefinition;
import org.teapotech.blockly.block.def.annotation.ApplyToBlock;
import org.teapotech.blockly.block.execute.AbstractBlockExecutor;
import org.teapotech.blockly.model.ToolboxItem;
import org.teapotech.blockly.model.ToolboxItem.Kind;
import org.teapotech.blockly.resource.BlockOptionProvider;

public class BlockRegistry {

    private static Logger LOG = LoggerFactory.getLogger(BlockRegistry.class);
    private final Map<BlockDefinition, Class<? extends AbstractBlockExecutor>> blockExecutors = new HashMap<>();
    private final BlockOptionProvider blockOptionProvider;
    private final ToolboxItem toolbox = new ToolboxItem();

    public BlockRegistry(BlockOptionProvider blockOptionProvider) {
        this.blockOptionProvider = blockOptionProvider;
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

    public ToolboxItem getToolbox() {
        return this.toolbox;
    }

    public Stream<BlockDefinition> getAllBlocks() {
        return this.blockExecutors.keySet().stream();
    }

    public void loadBlockExecutors() {

        Reflections reflections = new Reflections("org.teapotech");

        Set<Class<? extends AbstractBlockExecutor>> executorClasses = reflections
                .getSubTypesOf(AbstractBlockExecutor.class);

        for (Class<? extends AbstractBlockExecutor> c : executorClasses) {
            ApplyToBlock a = c.getDeclaredAnnotation(ApplyToBlock.class);
            if (a == null) {
                continue;
            }

            String blockType = a.blockType();
            if (!StringUtils.isBlank(blockType)) {
                String style = a.style();
                DefaultBlockDefinition bdef = new DefaultBlockDefinition(blockType, a.category(), style);
                blockExecutors.put(bdef, c);
                String category = bdef.getCategory();
                String[] ss = category.split("\\s*/\\s*");
                ToolboxItem tbCategory = this.toolbox;
                for (String subCat : ss) {
                    tbCategory = findOrInitiateCategory(tbCategory, subCat);
                }
                tbCategory.getContents().add(new ToolboxItem(bdef));
                LOG.info("Registered block, Type: {}, Executor class: {}", blockType, c.getName());
            } else {
                Class<? extends BlockDefinition> blockDefClass = a.value();
                if (Modifier.isAbstract(c.getModifiers())) {
                    continue;
                }
                if (blockDefClass.isAssignableFrom(CustomBlockDefinition.class)) {
                    LOG.error("{} is not derived from CustomBlockDefinition.class", blockDefClass);
                    continue;
                }
                try {
                    CustomBlockDefinition blockDef = (CustomBlockDefinition) blockDefClass
                            .getConstructor((Class<?>[]) null).newInstance();
                    if (blockOptionProvider != null) {
                        blockDef.setBlockOptions(blockOptionProvider.getOptions(blockType));
                    }
                    blockExecutors.put(blockDef, c);
                    String category = blockDef.getCategory();
                    String[] ss = category.split("\\s*/\\s*");
                    ToolboxItem tbCategory = this.toolbox;
                    for (String subCat : ss) {
                        tbCategory = findOrInitiateCategory(tbCategory, subCat);
                    }
                    tbCategory.getContents().add(new ToolboxItem(blockDef));
                    LOG.info("Registered block, Type: {}, Executor class: {}", blockDef.getBlockType(), c.getName());
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    private void registerBlockExecutor(BlockDefinition blockDef, Class<? extends AbstractBlockExecutor> executorClass) {
        this.blockExecutors.put(blockDef, executorClass);
        LOG.info("Registered block, Type: {}, Executor class: {}", blockDef.getBlockType(), executorClass.getName());
    }

    public List<CustomBlockConfiguration> getCustomBlockConfigurations() {
        List<CustomBlockConfiguration> result = new ArrayList<>();
        blockExecutors.keySet().stream().filter(bdef -> bdef instanceof CustomBlockDefinition).forEach(bdef -> {
            CustomBlockDefinition cbDef = (CustomBlockDefinition) bdef;

//            if (ClassUtils.isAssignable(bdef.getClass(), FileResourceSupport.class)) {
//                FileResourceSupport frs = (FileResourceSupport) bdef;
//                List<UserFileResource> allUserFileRes = userFileResourceProvider.findAll();
//                frs.setFileResources(allUserFileRes.toArray(new FileResource[] {}));
//            }
            try {
                String blockConf = cbDef.getConfiguration();
                if (StringUtils.isBlank(blockConf)) {
                    return;
                }
                result.add(new CustomBlockConfiguration(cbDef.getBlockType(), blockConf));
            } catch (Exception e) {
                LOG.error("Invalid block definition. Ignore it. \n{}", e.getMessage());
            }
        });

        return result;
    }

    private ToolboxItem findOrInitiateCategory(ToolboxItem item, String name) {
        ToolboxItem result = item.findCategoryByName(name);
        if (result == null) {
            result = new ToolboxItem(Kind.category);
            result.setName(name);
            LOG.info("Initiate toolbox category: {}", name);
            if (item.getContents() == null) {
                item.setContents(new ArrayList<>());
            }
            item.getContents().add(result);
        }
        return result;
    }
}
