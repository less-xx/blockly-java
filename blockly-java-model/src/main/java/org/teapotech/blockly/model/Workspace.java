package org.teapotech.blockly.model;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * @author jiangl
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "xml", namespace = BlocklyConstants.NAMESPACE)
public class Workspace {

	@JacksonXmlProperty(isAttribute = true)
	private String id;

	@JacksonXmlProperty(isAttribute = true)
	private String style = "display: none";

	@JacksonXmlProperty(isAttribute = false, localName = "variable", namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = true, localName = "variables", namespace = BlocklyConstants.NAMESPACE)
	private List<Variable> variables;

	@JacksonXmlProperty(localName = "category", isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Category> categories;

	@JacksonXmlProperty(localName = "block", isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Block> blocks = null;

	@JacksonXmlProperty(localName = "shadow", isAttribute = false, namespace = BlocklyConstants.NAMESPACE)
	@JacksonXmlElementWrapper(useWrapping = false)
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
