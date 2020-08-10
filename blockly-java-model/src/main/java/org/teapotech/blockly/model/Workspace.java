package org.teapotech.blockly.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * @author jiangl
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "xml")
@JacksonXmlRootElement(localName = "xml")
public class Workspace {

	@JacksonXmlProperty
	private String id;

	@XmlAttribute
	@JacksonXmlProperty
	private String style = "display: none";

	@XmlElementWrapper(name = "variables")
	@XmlElement(name = "variable")
	@JacksonXmlProperty(isAttribute = false)
	private List<Variable> variables;

	@XmlElement(name = "category")
	@JacksonXmlProperty
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Category> categories = new ArrayList<>();

	@JacksonXmlProperty(localName = "block", isAttribute = false)
	@JacksonXmlElementWrapper(useWrapping = false)
	@XmlElement(name = "block")
	private List<Block> blocks = null;

	@JacksonXmlProperty(localName = "shadow", isAttribute = false)
	@JacksonXmlElementWrapper(useWrapping = false)
	@XmlElement(name = "shadow")
	private List<Shadow> shadows = null;

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Shadow> getShadows() {
		return shadows;
	}

	public void setShadows(List<Shadow> shadows) {
		this.shadows = shadows;
	}

	public Category findCategoryById(String idChain) {
		String[] cc = idChain.split("\\s*/\\s*");
		Category cat = null;
		List<Category> cl = this.categories;
		for (String cid : cc) {
			Optional<Category> op = cl.stream().filter(c -> c.getId().equalsIgnoreCase(cid)).findFirst();
			if (!op.isPresent()) {
				return null;
			}
			cat = op.get();
			cl = cat.getCategories();
		}
		return cat;
	}
}
