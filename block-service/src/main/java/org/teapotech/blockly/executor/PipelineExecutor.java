package org.teapotech.blockly.executor;

import org.teapotech.blockly.entity.PipelineExecution;

public interface PipelineExecutor {

    void execute(PipelineExecution pipelineExec) throws Exception;

}
