package org.teapotech.blockly.block.def;

public interface BlockDefinition {

	public static class Category {

		public final static String ID_BASIC = "basic";
		public final static String ID_EVENTS = "events";
		public final static String ID_START_STOP = "start_stop";
		public final static String ID_RESOURCES = "resources";
		public final static String ID_VARIABLES = "variables";
		public final static String ID_FILE_OPERATIONS = "file_operations";
		public final static String ID_CONTROL = "control";
	}

	String getConfiguration();

	String getBlockType();

	String getCategory();

}
