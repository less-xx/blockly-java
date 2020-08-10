/**
 * 
 */
package org.teapotech.blockly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author jiangl
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class BlockValue {

	@XmlAttribute
	@JacksonXmlProperty
	private String name;

	@XmlElement
	@JacksonXmlProperty(isAttribute = false)
	private Shadow shadow;

	@XmlElement
	@JacksonXmlProperty(isAttribute = false)
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
