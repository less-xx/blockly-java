package org.teapotech.blockly.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.teapotech.blockly.TestAppConfig;
import org.teapotech.blockly.entity.PipelineConfiguration;
import org.teapotech.blockly.repo.PipelineConfigRepo;
import org.teapotech.blockly.util.BlockRegistry;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ContextConfiguration(classes = {TestAppConfig.class})
public class TestPipelineConfigurationService {

    @MockBean
    PipelineConfigRepo pipelineConfRepo;

    @MockBean
    BlockRegistry blockRegistry;

    @Autowired
    PipelineDatastoreService pipelineDatastoreService;

    @Test
    void testSavePipelineConfig() {
        PipelineConfiguration pipelineConfiguration = new PipelineConfiguration();
        when(pipelineConfRepo.save(any(PipelineConfiguration.class))).thenReturn(pipelineConfiguration);
        pipelineConfRepo.save(pipelineConfiguration);

    }

}
