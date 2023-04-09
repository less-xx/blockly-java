package org.teapotech.blockly.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.teapotech.blockly.entity.PipelineConfiguration;

public interface PipelineDatastoreService {

    PipelineConfiguration savePipelineConfig(PipelineConfiguration entity);

    Page<PipelineConfiguration> getPipelineConfig(Pageable pageable);

    PipelineConfiguration findByName(String name);

    PipelineConfiguration findById(String id);
}
