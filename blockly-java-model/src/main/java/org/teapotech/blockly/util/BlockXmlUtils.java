/**
 * 
 */
package org.teapotech.blockly.util;

import java.io.IOException;
import java.io.InputStream;

import org.teapotech.blockly.model.Block;
import org.teapotech.blockly.model.Workspace;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author jiangl
 *
 */
public class BlockXmlUtils {

	private static XmlMapper xmlMapper = new XmlMapper();

	public static Workspace loadWorkspace(String workspaceXml) throws JsonMappingException, JsonProcessingException {
		Workspace w = xmlMapper.readValue(workspaceXml, Workspace.class);
		return w;
	}

	public static Workspace loadWorkspace(InputStream in) throws JsonParseException, JsonMappingException, IOException {
		Workspace w = xmlMapper.readValue(in, Workspace.class);
		return w;
	}

	public static Block loadToolboxBlock(InputStream in) throws JsonParseException, JsonMappingException, IOException {
		Workspace tbw = loadWorkspace(in);
		if (tbw == null || tbw.getBlocks() == null || tbw.getBlocks().isEmpty()) {
			return null;
		}
		return tbw.getBlocks().get(0);
	}

	public static String toXml(Object o) throws JsonProcessingException {
		return xmlMapper.writeValueAsString(o);
	}
}
