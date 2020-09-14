package org.teapotech.blockly.workspace;

import java.io.InputStream;

import org.teapotech.blockly.model.Workspace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class WorkspaceXmlParser {

	private static XmlMapper xmlMapper = new XmlMapper();

	public Workspace loadWorkspace(String configuration) throws Exception {
		Workspace w = xmlMapper.readValue(configuration, Workspace.class);
		return w;
	}

	public static Workspace loadWorkspace(InputStream in) throws Exception {
		Workspace w = xmlMapper.readValue(in, Workspace.class);
		return w;
	}

	public static String getXml(Workspace workspace) throws JsonProcessingException {
		return xmlMapper.writeValueAsString(workspace);
	}

}
