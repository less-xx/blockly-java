package org.teapotech.blockly.block.def;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teapotech.blockly.resource.BlockOption;

public abstract class CustomBlockDefinition implements BlockDefinition {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    protected List<BlockOption> blockOptions;

    private long executionTimeout;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        BlockDefinition bdef = (BlockDefinition) obj;
        if (bdef.getBlockType() != null && this.getBlockType() != null) {
            return bdef.getBlockType().equals(this.getBlockType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (getClass().getName() + "#" + this.getBlockType()).hashCode() * 109;
    }

    public long getExecutionTimeout() {
        return executionTimeout;
    }

    public void setExecutionTimeout(long executionTimeout) {
        this.executionTimeout = executionTimeout;
    }

    public List<BlockOption> getBlockOptions() {
        return blockOptions;
    }

    public void setBlockOptions(List<BlockOption> blockOptions) {
        this.blockOptions = blockOptions;
    }

}
