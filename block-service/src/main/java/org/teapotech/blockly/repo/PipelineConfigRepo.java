package org.teapotech.blockly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teapotech.blockly.entity.PipelineConfiguration;

public interface PipelineConfigRepo extends JpaRepository<PipelineConfiguration, String> {

    PipelineConfiguration findOneByName(String name);

}
