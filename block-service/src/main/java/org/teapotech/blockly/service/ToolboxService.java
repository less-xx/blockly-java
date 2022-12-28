package org.teapotech.blockly.service;

import org.teapotech.blockly.model.ToolboxItem;

public interface ToolboxService {

    ToolboxItem getToolboxConfiguration(ToolboxBlockFilter filter);
}
