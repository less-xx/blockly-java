package org.teapotech.blockly.web;

import jakarta.validation.constraints.NotEmpty;

public class PipelineConfigRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String configuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

}
