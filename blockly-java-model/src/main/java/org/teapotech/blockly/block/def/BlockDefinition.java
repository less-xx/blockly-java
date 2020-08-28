package org.teapotech.blockly.block.def;

public interface BlockDefinition {

	public static class CategoryID {

		public final static String ID_CONTROL = "control";
		public final static String ID_EVENTS = "events";
		public final static String ID_FILE_OPERATIONS = "file_operations";
		public final static String ID_MATH = "math";
		public final static String ID_RESOURCES = "resources";
		public final static String ID_START_EXIT = "start_exit";
		public final static String ID_VARIABLES = "variables";

	}

	String getConfiguration();

	String getBlockType();

	String getCategory();

}
