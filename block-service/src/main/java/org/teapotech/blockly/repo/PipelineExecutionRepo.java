package org.teapotech.blockly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teapotech.blockly.entity.PipelineExecution;

public interface PipelineExecutionRepo extends JpaRepository<PipelineExecution, Long> {


}
