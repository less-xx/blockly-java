package org.teapotech.blockly.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {

    private String id;

    private String name;

    private String variableType;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
