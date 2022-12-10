package org.teapotech.blockly.service;

import java.util.List;

import org.teapotech.blockly.block.def.CustomBlockConfiguration;

public interface BlockService {

    List<CustomBlockConfiguration> getCustomBlockConfigurations();
}
