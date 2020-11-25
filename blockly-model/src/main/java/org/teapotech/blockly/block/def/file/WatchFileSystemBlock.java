package org.teapotech.blockly.block.def.file;

import org.teapotech.blockly.block.def.CustomBlockDefinition;
import org.teapotech.blockly.resource.FileResource;
import org.teapotech.blockly.resource.FileResourceSupport;

public class WatchFileSystemBlock extends CustomBlockDefinition implements FileResourceSupport {
	public final static String EVENT_CREATED = "CREATED";
	public final static String EVENT_CHANGED = "CHANGED";
	public final static String EVENT_DELETED = "DELETED";

	public final static String BLOCK_TYPE = "watch_file_system";

	private FileResource[] fileResources;

	@Override
	public String getBlockType() {
		return BLOCK_TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_FILE_OPERATIONS;
	}

	@Override
	public void setFileResources(FileResource[] fileResources) {
		this.fileResources = fileResources;
	}

	@Override
	public String getConfiguration() {
		String resOptions = "";
		if (this.fileResources != null) {
			for (FileResource fr : fileResources) {
				String op = "[\"" + (fr.isDirectory() ? "\u1F5C0" : "\u1F5CE") + " " + fr.getName() + "\",\""
						+ fr.getId() + "\"]";
				resOptions = resOptions + op + ",";
			}
		}
		if (resOptions.endsWith(",")) {
			resOptions = resOptions.substring(0, resOptions.length() - 1);
		}

		return """
				{
					"type": "watch_file_system",
					"message0": "Watch %1 and %2 broadcast %3",
					"args0": [
					  {
					    "type": "field_dropdown",
					    "name": "FILE_RESOURCE",
					    "options": [
					    """ + resOptions + """
				      ]
				    },
				    {
				      "type": "input_dummy"
				    },
				    {
				      "type": "field_input",
				      "name": "EVENT",
				      "text": "fileCreateUpdateDeleteEvents"
				    }
				  ],
				  "previousStatement": null,
				  "style": "event_blocks",
				  "tooltip": "The event is an object {'event': 'CREATED|CHANGED|DELETED', 'file':'File object'}",
				  "helpUrl": ""
				}

				""";
	}
}
