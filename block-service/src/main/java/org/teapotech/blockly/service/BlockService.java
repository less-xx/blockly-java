package org.teapotech.blockly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface BlockService {

    List<JsonNode> getCustomBlockConfigurations();
}
