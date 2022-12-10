package org.teapotech.blockly.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teapotech.blockly.block.def.BlockDefinition;
import org.teapotech.blockly.block.def.CustomBlockConfiguration;
import org.teapotech.blockly.util.BlockRegistry;

@Service
public class BlockServiceImpl implements BlockService {

    private static Logger LOG = LoggerFactory.getLogger(BlockServiceImpl.class);

    @Autowired
    BlockRegistry blockRegistry;

    @PostConstruct
    void init() {
        blockRegistry.loadBlockExecutors();
    }

    Stream<BlockDefinition> getAllRegisteredBlocks() {
        return blockRegistry.getAllBlocks();
    }

    @Override
    public List<CustomBlockConfiguration> getCustomBlockConfigurations() {
        return blockRegistry.getCustomBlockConfigurations();
    }
}
