package org.teapotech.blockly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pipeline.executor")
public class PipelineExecutorProperties {

    public static enum ExecutorType {
        LOCAL, DOCKER
    }

    private ExecutorType type;
    private String outputDir;
    private Docker docker;

    public ExecutorType getType() {
        return type;
    }

    public void setType(ExecutorType executorType) {
        this.type = executorType;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public Docker getDocker() {
        return docker;
    }

    public void setDocker(Docker docker) {
        this.docker = docker;
    }

    /**
     *
     */
    public static class Docker {
        private String host;
        private String image;
        private boolean deleteContainer;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isDeleteContainer() {
            return deleteContainer;
        }

        public void setDeleteContainer(boolean deleteContainer) {
            this.deleteContainer = deleteContainer;
        }
    }

}
