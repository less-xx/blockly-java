package org.teapotech.blockly.workspace;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.teapotech.blockly.util.JSONUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class WorkspaceManager {

	private final Map<String, JsonNode> defaultCategories = new HashMap<>();

	private void loadDefaultCategories() throws IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream("category-definition.json");
		ArrayNode an = (ArrayNode) JSONUtils.getObject(in);
		Iterator<JsonNode> it = an.iterator();
		while (it.hasNext()) {
			JsonNode n = it.next();
			String id = n.get("id").asText();
			defaultCategories.put(id, n);
		}
	}

}
