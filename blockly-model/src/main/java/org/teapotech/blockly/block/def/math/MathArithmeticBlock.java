package org.teapotech.blockly.block.def.math;

import org.teapotech.blockly.block.def.CustomBlockDefinition;

public class MathArithmeticBlock extends CustomBlockDefinition {

	public final static String TYPE = "math_arithmetic";

	@Override
	public String getBlockType() {
		return TYPE;
	}

	@Override
	public String getCategory() {
		return CategoryID.ID_MATH;
	}

	@Override
	public String getConfiguration() {
		return """
				{
				  "type": "math_arithmetic",
				  "message0": "%1 %2 %3 %4",
				  "args0": [
				    {
				      "type": "input_value",
				      "name": "A",
				      "check": "Number",
				      "align": "CENTRE"
				    },
				    {
				      "type": "field_dropdown",
				      "name": "OP",
				      "options": [
				        [
				          "+",
				          "ADD"
				        ],
				        [
				          "-",
				          "MINUS"
				        ],
				        [
				          "×",
				          "MULTIPLY"
				        ],
				        [
				          "÷",
				          "DIVIDE"
				        ],
				        [
				          "%",
				          "MOD"
				        ]
				      ]
				    },
				    {
				      "type": "input_dummy",
				      "align": "CENTRE"
				    },
				    {
				      "type": "input_value",
				      "name": "B",
				      "check": "Number",
				      "align": "CENTRE"
				    }
				  ],
				  "inputsInline": true,
				  "output": "Number",
				  "style": "math_blocks",
				  "tooltip": "",
				  "helpUrl": ""
				}
				""";
	}

}
