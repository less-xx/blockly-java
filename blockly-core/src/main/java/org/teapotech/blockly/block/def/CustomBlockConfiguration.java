/**
 * 
 */
package org.teapotech.blockly.block.def;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomBlockConfiguration {

    private final String type;

    @JsonRawValue
    private final String definition;

    public CustomBlockConfiguration(String type, String definition) {
        this.type = type;
        this.definition = definition;
    }

    public String getType() {
        return type;
    }

    public String getDefinition() {
        return definition;
    }
}
