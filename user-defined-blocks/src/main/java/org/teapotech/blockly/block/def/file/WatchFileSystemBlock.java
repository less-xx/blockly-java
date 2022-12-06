package org.teapotech.blockly.block.def.file;

import org.teapotech.blockly.block.def.CustomBlockDefinition;
import org.teapotech.blockly.resource.BlockOption;

public class WatchFileSystemBlock extends CustomBlockDefinition {
    public final static String EVENT_CREATED = "CREATED";
    public final static String EVENT_CHANGED = "CHANGED";
    public final static String EVENT_DELETED = "DELETED";

    public final static String BLOCK_TYPE = "watch_file_system";

    @Override
    public String getBlockType() {
        return BLOCK_TYPE;
    }

    @Override
    public String getCategory() {
        return CategoryID.ID_FILE_OPERATIONS;
    }

    @Override
    public String getConfiguration() {
        String resOptions = "";
        if (this.blockOptions != null) {
            for (BlockOption bOp : blockOptions) {
                String op = "[\"" + (bOp.getIcon() == null ? "\u1F5C0" : bOp.getIcon()) + " " + bOp.getName() + "\",\""
                        + bOp.getId() + "\"]";
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
