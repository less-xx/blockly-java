/**
 * 
 */
package org.teapotech.blockly.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shadow {

    private String type;

    private String id;

    private LinkedHashMap<String, Serializable> fields;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedHashMap<String, Serializable> getFields() {
        return fields;
    }

    public void setFields(LinkedHashMap<String, Serializable> fields) {
        this.fields = fields;
    }

    public Serializable getFieldcValue() {
        if (this.fields == null) {
            return null;
        }
        Iterator<Entry<String, Serializable>> it = this.fields.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        return it.next().getValue();
    }

    public Serializable getFieldcValue(String field) {
        if (this.fields == null) {
            return null;
        }
        return this.fields.get(field);
    }
}
