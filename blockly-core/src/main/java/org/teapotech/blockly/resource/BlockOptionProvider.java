package org.teapotech.blockly.resource;

import java.util.List;

public interface BlockOptionProvider {

    List<BlockOption> getOptions(String blockType);

}
