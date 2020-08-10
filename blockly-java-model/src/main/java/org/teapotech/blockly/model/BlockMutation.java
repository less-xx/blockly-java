/**
 * 
 */
package org.teapotech.blockly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author jiangl
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BlockMutation {

	@XmlAttribute
	@JacksonXmlProperty
	private Integer items;

	@XmlAttribute(name = "elseif")
	@JacksonXmlProperty
	private Integer elseif;

	@XmlAttribute(name = "else")
	@JacksonXmlProperty
	private Integer _else;

	public Integer getItems() {
		return items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public Integer getElseif() {
		return elseif;
	}

	public void setElseif(Integer elseif) {
		this.elseif = elseif;
	}

	public Integer getElse() {
		return _else;
	}

	public void setElse(Integer _else) {
		this._else = _else;
	}

}
