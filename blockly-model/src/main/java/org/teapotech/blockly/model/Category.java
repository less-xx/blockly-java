/**
 * 
 */
package org.teapotech.blockly.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

	@JsonIgnore
	private String id;

	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(localName = "categorystyle", isAttribute = true)
	private String style;

	@JacksonXmlProperty(isAttribute = true)
	private String colour;

	@JacksonXmlProperty(localName = "category", isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Category> categories;

	@JacksonXmlProperty(localName = "block", isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Block> blocks;

	public Category() {
	}

	public Category(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public String getCategorystyle() {
		return style;
	}

	public void setCategorystyle(String categorystyle) {
		this.style = categorystyle;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Category: " + this.name;
	}
}
