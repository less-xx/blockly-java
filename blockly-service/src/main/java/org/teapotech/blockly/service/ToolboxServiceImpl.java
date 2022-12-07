package org.teapotech.blockly.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teapotech.blockly.block.def.CategoryDefinition;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Category;
import org.teapotech.blockly.model.Toolbox;
import org.teapotech.blockly.util.JsonHelper;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ToolboxServiceImpl {

    private static Logger LOG = LoggerFactory.getLogger(ToolboxServiceImpl.class);
    private final Map<String, CategoryDefinition> categories = new TreeMap<>();

    @Autowired
    BlockServiceImpl blockService;

    @Autowired
    JsonHelper jsonHelper;

    @PostConstruct
    void init() throws Exception {
        loadDefaultCategories();
    }

    public Toolbox getToolboxConfiguration(ToolboxBlockFilter filter) {
        Toolbox toolbox = new Toolbox();
        blockService.getAllRegisteredBlocks().forEach(bdef -> {
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
        List<CategoryDefinition> list = jsonHelper.getObject(in, new TypeReference<List<CategoryDefinition>>() {
        });
        for (CategoryDefinition def : list) {
            categories.put(def.getId(), def);
        }
    }

    public Category findCategory(Toolbox toolbox, String idChain) {
        String[] cc = idChain.split("\\s*/\\s*");
        Category cat = null;
        List<Category> cl = toolbox.getCategories();
        if (cl == null) {
            cl = new ArrayList<>();
            toolbox.setCategories(cl);
        }
        for (String cid : cc) {
            Optional<Category> op = cl.stream().filter(c -> c.getId().equalsIgnoreCase(cid)).findFirst();
            if (op.isPresent()) {
                cat = op.get();
                cl = cat.getCategories();
            } else {
                CategoryDefinition n = this.categories.get(cid);
                if (n == null) {
                    LOG.error("Cannot find category by ID: {}", cid);
                    continue;
                }
                cat = new Category();
                cat.setId(cid);
                cat.setName(n.getName());
                cat.setCategorystyle(n.getStyle());
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

//    public List<CustomBlockConfiguration> getCustomBlockConfigurations() {
//        List<CustomBlockConfiguration> result = new ArrayList<>();
//        blockExecutors.keySet().stream().filter(bdef -> bdef instanceof CustomBlockDefinition).forEach(bdef -> {
//            CustomBlockDefinition cbDef = (CustomBlockDefinition) bdef;
//
//            if (ClassUtils.isAssignable(bdef.getClass(), FileResourceSupport.class)) {
//                FileResourceSupport frs = (FileResourceSupport) bdef;
//                List<UserFileResource> allUserFileRes = userFileResourceProvider.findAll();
//                frs.setFileResources(allUserFileRes.toArray(new FileResource[] {}));
//            }
//            try {
//                String blockConf = cbDef.getConfiguration();
//                if (StringUtils.isBlank(blockConf)) {
//                    return;
//                }
//                JsonNode json = JSONUtils.getObject(blockConf);
//                result.add(new CustomBlockConfiguration(cbDef.getBlockType(), json));
//            } catch (Exception e) {
//                LOG.error("Invalid block definition. Ignore it. \n{}", e.getMessage());
//            }
//        });
//
//        return result;
//    }

    public void registerCategory(CategoryDefinition catDef) {
        this.categories.put(catDef.getId(), catDef);
    }

    public Block getToolboxConfig(String blockType) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("toolbox-config/" + blockType + ".json");
        if (in == null) {
            Block b = new Block();
            b.setType(blockType);
            return b;
        }
        return jsonHelper.getObject(in, Block.class);

    }
}
