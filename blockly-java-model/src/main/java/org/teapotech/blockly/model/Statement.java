/**
 * 
 */
package org.teapotech.blockly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author jiangl
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Statement {

	@XmlAttribute
	@JacksonXmlProperty
	private String name;

	@XmlElement(name = "block")
	@JacksonXmlProperty(isAttribute = false)
	private Block block;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

}
