/**
 * 
 */
package org.teapotech.blockly.util;

/**
 * @author jiangl
 *
 */
public class BlockXmlUtils {

//	private static XmlMapper xmlMapper = new XmlMapper();
//
//	public static Workspace loadWorkspace(String workspaceXml) throws JsonMappingException, JsonProcessingException {
//		Workspace w = xmlMapper.readValue(workspaceXml, Workspace.class);
//		return w;
//	}
//
//	public static Workspace loadWorkspace(InputStream in) throws JsonParseException, JsonMappingException, IOException {
//		Workspace w = xmlMapper.readValue(in, Workspace.class);
//		return w;
//	}
//
//	public static Block loadToolboxBlock(InputStream in) throws JsonParseException, JsonMappingException, IOException {
//		Workspace tbw = loadWorkspace(in);
//		if (tbw == null || tbw.getBlocks() == null || tbw.getBlocks().isEmpty()) {
//			return null;
//		}
//		return tbw.getBlocks().get(0);
//	}
//
//	public static String toXml(Object o) throws JsonProcessingException {
//		return xmlMapper.writeValueAsString(o);
//	}
//
//	public static Workspace updateToUUID(Workspace w) {
//		Map<String, String> idMap = new HashMap<>();
//		String prefix = "workspace_";
//		if (w.getId() != null && !w.getId().startsWith(prefix)) {
//			w.setId(prefix + randomID());
//		}
//		List<Variable> vars = w.getVariables();
//		if (vars != null) {
//			vars.forEach(var -> updateID(var, idMap));
//		}
//		if (w.getBlocks() != null) {
//			w.getBlocks().forEach(b -> updateID(b, idMap));
//		}
//		if (w.getCategories() != null) {
//			w.getCategories().forEach(c -> {
//				String pref = "cat_";
//				if (c.getId() != null && !c.getId().startsWith(pref)) {
//					c.setId(pref + randomID());
//				}
//			});
//		}
//		List<Shadow> shadows = w.getShadows();
//		if (shadows != null) {
//			shadows.forEach(s -> updateID(s, idMap));
//		}
//		return w;
//	}
//
//	private static void updateID(Block block, Map<String, String> idMap) {
//		String prefix = block.getType() + "_";
//		if (!block.getId().startsWith(prefix)) {
//			String id = idMap.get(block.getId());
//			if (id == null) {
//				id = prefix + randomID();
//				idMap.put(block.getId(), id);
//				block.setId(id);
//			}
//		}
//		List<Field> fields = block.getFields();
//		if (fields != null) {
//			fields.forEach(f -> updateID(f, idMap));
//		}
//		List<BlockValue> values = block.getValues();
//		if (values != null) {
//			values.forEach(v -> updateID(v, idMap));
//		}
//		List<Statement> statements = block.getStatements();
//		if (statements != null) {
//			statements.forEach(stmt -> updateID(stmt, idMap));
//		}
//		Next next = block.getNext();
//		if (next != null && next.getBlock() != null) {
//			updateID(next.getBlock(), idMap);
//		}
//	}
//
//	private static void updateID(Field field, Map<String, String> idMap) {
//		String prefix = "field_";
//		if (field.getId() != null && !field.getId().startsWith(prefix)) {
//			String id = idMap.get(field.getId());
//			if (id == null) {
//				id = prefix + randomID();
//				idMap.put(field.getId(), id);
//				field.setId(id);
//			}
//		}
//	}
//
//	private static void updateID(Statement stmt, Map<String, String> idMap) {
//		if (stmt.getBlock() != null) {
//			updateID(stmt.getBlock(), idMap);
//		}
//	}
//
//	private static void updateID(BlockValue blockValue, Map<String, String> idMap) {
//		if (blockValue.getBlock() != null) {
//			updateID(blockValue.getBlock(), idMap);
//		}
//		if (blockValue.getShadow() != null) {
//			updateID(blockValue.getShadow(), idMap);
//		}
//	}
//
//	private static void updateID(Variable var, Map<String, String> idMap) {
//		String prefix = "var_";
//		if (var.getId() != null && !var.getId().startsWith(prefix)) {
//			String id = idMap.get(var.getId());
//			if (id == null) {
//				id = prefix + randomID();
//				idMap.put(var.getId(), id);
//				var.setId(id);
//			}
//		}
//	}
//
//	private static void updateID(Shadow shadow, Map<String, String> idMap) {
//		String prefix = "shadow_";
//		if (shadow.getId() != null && !shadow.getId().startsWith(prefix)) {
//			String id = idMap.get(shadow.getId());
//			if (id == null) {
//				id = prefix + randomID();
//				idMap.put(shadow.getId(), id);
//				shadow.setId(id);
//			}
//		}
//	}
//
//	private static String randomID() {
//		return RandomStringUtils.randomAlphanumeric(8);
//	}
}
