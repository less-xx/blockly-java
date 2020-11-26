package org.teapotech.blockly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.teapotech.blockly.block.def.BlockExecutorRegistry;
import org.teapotech.blockly.block.def.CustomBlockConfiguration;
import org.teapotech.blockly.model.Workspace;

@RestController
@CrossOrigin("*")
public class BlockController {

	@Autowired
	BlockExecutorRegistry blockExecutorRegistry;

	@GetMapping(value = "/block-definitions")
	@ResponseBody
	public List<CustomBlockConfiguration> getAllBlockDefinitions() throws Exception {
		return blockExecutorRegistry.getCustomBlockConfigurations();
	}

	@GetMapping(value = "/toolbox-config", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Workspace getAllBlockRegistries() throws Exception {
		return blockExecutorRegistry.getToolboxConfiguration();
	}
}
