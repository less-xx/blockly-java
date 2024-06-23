/**
 * 
 */
package org.teapotech.blockly.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Less Jiang
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class Block {

    public static class FieldType {
        public final static String NUM = "NUM";
        public final static String OP = "OP";
        public final static String TEXT = "TEXT";
        public final static String VAR = "VAR";
    }

    public static class InputType {
        public final static String ADD = "ADD";
        public final static String BOOL = "BOOL";
        public final static String DO = "DO";
        public final static String ELSE = "ELSE";
        public final static String FROM = "FROM";
        public final static String NUM = "NUM";
        public final static String IF = "IF";
        public final static String TEXT = "TEXT";
        public final static String TO = "TO";
        public final static String VALUE = "VALUE";
    }

    public static class ExtraState {
        public final static String ITEM_COUNT = "itemCount";
        public final static String ELSE_IF_COUNT = "elseIfCount";
        public final static String hasElse = "hasElse";
    }

    private BlockMutation mutation;

    private String type;

    private String id;

    private Integer x;

    private Integer y;

    private LinkedHashMap<String, Input> inputs;// The value type can be either Block or Shadow

    private Next next;

    private LinkedHashMap<String, Serializable> fields;

    private LinkedHashMap<String, Serializable> extraState;

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

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public BlockMutation getMutation() {
        return mutation;
    }

    public void setMutation(BlockMutation mutation) {
        this.mutation = mutation;
    }

    public LinkedHashMap<String, Input> getInputs() {
        return inputs;
    }

    public void setInputs(LinkedHashMap<String, Input> inputs) {
        this.inputs = inputs;
    }

    public LinkedHashMap<String, Serializable> getFields() {
        return fields;
    }

    public void setFields(LinkedHashMap<String, Serializable> fields) {
        this.fields = fields;
    }

    public LinkedHashMap<String, Serializable> getExtraState() {
        return extraState;
    }

    public void setExtraState(LinkedHashMap<String, Serializable> extraState) {
        this.extraState = extraState;
    }

    public Serializable getFieldValue() {
        if (this.fields == null) {
            return null;
        }
        Iterator<Entry<String, Serializable>> it = this.fields.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        return it.next().getValue();
    }

    @SuppressWarnings("unchecked")
    public Serializable getFieldValue(String field) {
        if (this.fields == null) {
            return null;
        }
        if (FieldType.VAR.equals(field)) {
            Object fieldObj = this.fields.get(field);
            if( fieldObj instanceof Map<?,?>) {
                Map<String, Object> vars = (Map<String, Object>) this.fields.get(field);
                return new Variable((String) vars.get("id"), (String) vars.get("name"));
            }
        }
        return this.fields.get(field);
    }

    @Override
    public String toString() {
        return "Block [type=" + type + ", id=" + id + ", next=" + next + "]";
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Next {

        private Block block;

        public Block getBlock() {
            return block;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        @Override
        public String toString() {
            return "Next [block=" + block + "]";
        }
    }

}
