package org.teapotech.blockly.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.teapotech.blockly.model.BlockDef;
import org.teapotech.blockly.model.BlockDef.ValueType;
import org.teapotech.blockly.model.BlockDefArg;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BlockDefSerializer extends StdSerializer<BlockDef> {

    private static final long serialVersionUID = 5059632914672689100L;

    protected BlockDefSerializer() {
        super(BlockDef.class);
    }

    @Override
    public void serialize(BlockDef value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", value.getType());
        StringBuilder messageBuilder = new StringBuilder();
        List<BlockDefArg> args = value.getArgs();
        if (args != null) {
            gen.writeArrayFieldStart("args0");
            int idx = 1;
            for (BlockDefArg bFld : args) {
                provider.defaultSerializeValue(bFld, gen);
                messageBuilder.append(bFld.getText()).append(" ").append("%").append(idx++).append(" ");
            }
            gen.writeEndArray();
        }
        provider.defaultSerializeField("message0", messageBuilder.toString(), gen);

        ValueType[] outputValue = value.getOutput();
        if (outputValue != null) {
            if (ArrayUtils.contains(outputValue, ValueType.Any)) {
                gen.writeNullField("output");
            } else {
                provider.defaultSerializeField("output", outputValue, gen);
            }
        }
        ValueType[] previousStatementValues = value.getPreviousStatement();
        if (previousStatementValues != null) {
            if (ArrayUtils.contains(previousStatementValues, ValueType.Any)) {
                gen.writeNullField("previousStatement");
            } else {
                provider.defaultSerializeField("previousStatement", previousStatementValues, gen);
            }
        }
        ValueType[] nextStatementValues = value.getNextStatement();
        if (nextStatementValues != null) {
            if (ArrayUtils.contains(nextStatementValues, ValueType.Any)) {
                gen.writeNullField("nextStatement");
            } else {
                provider.defaultSerializeField("nextStatement", nextStatementValues, gen);
            }
        }
        if (value.getColour() != null) {
            gen.writeNumberField("colour", value.getColour());
        }
        if (value.getTooltip() != null) {
            gen.writeStringField("tooltip", value.getTooltip());
        }
        if (value.getHelpUrl() != null) {
            gen.writeStringField("helpUrl", value.getHelpUrl());
        }
        if (value.getStyle() != null) {
            gen.writeStringField("style", value.getStyle());
        }

        gen.writeEndObject();
    }

}
