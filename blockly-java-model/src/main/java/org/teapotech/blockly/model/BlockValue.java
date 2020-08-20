/**
 * 
 */
package org.teapotech.blockly.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class BlockValue {

	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	private Shadow shadow;

	@JacksonXmlProperty(isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	private Block block;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Shadow getShadow() {
		return shadow;
	}

	public void setShadow(Shadow shadow) {
		this.shadow = shadow;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

}
