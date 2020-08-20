package org.teapotech.blockly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variable {

	@JacksonXmlProperty(isAttribute = true)
	private String type;

	@JacksonXmlProperty(isAttribute = true)
	private String id;

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
