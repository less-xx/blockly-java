package org.teapotech.blockly.block.execute;

public record WorkspaceExecutionConfig(String logLevel, int timeoutInSec) {

    public WorkspaceExecutionConfig(String logLevel, int timeoutInSec) {
        if (logLevel == null) {
            this.logLevel = "DEBUG";
        } else {
            this.logLevel = logLevel;
        }
        if (timeoutInSec <= 0) {
            this.timeoutInSec = 300;
        } else {
            this.timeoutInSec = timeoutInSec;
        }
    }
}
