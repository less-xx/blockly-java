package org.teapotech.blockly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@XmlAccessorType(XmlAccessType.FIELD)
public class Variable {

	@XmlAttribute
	@JacksonXmlProperty
	private String type = "";

	@XmlAttribute
	@JacksonXmlProperty
	private String id;

	@XmlValue
	@JacksonXmlText(value = true)
	private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "{id: " + this.id + ", var: " + this.value + "}";
	}
}
