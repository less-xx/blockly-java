package org.teapotech.blockly.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teapotech.blockly.block.def.CategoryDefinition;
import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.ToolboxItem;
import org.teapotech.blockly.util.JsonHelper;

import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ToolboxServiceImpl implements ToolboxService {

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

    @Override
    public ToolboxItem getToolboxConfiguration(ToolboxBlockFilter filter) {
        ToolboxItem toolbox = blockService.blockRegistry.getToolbox();
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


    private Block getToolboxConfig(String blockType) throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("toolbox-config/" + blockType + ".json");
        if (in == null) {
            Block b = new Block();
            b.setType(blockType);
            return b;
        }
        return jsonHelper.getObject(in, Block.class);

    }
}
