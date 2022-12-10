package org.teapotech.blockly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.teapotech.blockly.model.ToolboxItem;
import org.teapotech.blockly.service.BlockService;
import org.teapotech.blockly.service.ToolboxService;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin("*")
public class BlockController {

    @Autowired
    BlockService blockService;

    @Autowired
    ToolboxService toolboxService;

    @GetMapping(value = "/block-definitions")
    @ResponseBody
    public List<JsonNode> getAllBlockDefinitions() throws Exception {
        return blockService.getCustomBlockConfigurations();
    }

    @GetMapping(value = "/toolbox-config")
    @ResponseBody
    public ToolboxItem getAllBlockRegistries() throws Exception {
        return toolboxService.getToolboxConfiguration(null);
    }
}
