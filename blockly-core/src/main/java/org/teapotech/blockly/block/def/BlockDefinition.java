package org.teapotech.blockly.block.def;

public interface BlockDefinition {

    public static String INTERNAL_BLOCK_TYPE_VARIABLES_SET = "variables_set";
    public static String INTERNAL_BLOCK_TYPE_VARIABLES_GET = "variables_get";
    public static String INTERNAL_BLOCK_TYPE_TEXT = "text";

    public static String INTERNAL_BLOCK_TYPE_MULTILINE_TEXT = "multiline_text";
    public static String INTERNAL_BLOCK_TYPE_TEXT_PRINT = "text_print";
    public static String INTERNAL_BLOCK_TYPE_TEXT_JOIN = "text_join";
    public static String INTERNAL_BLOCK_TYPE_MATH_ROUND = "math_round";
    public static String INTERNAL_BLOCK_TYPE_MATH_RANDOM_INT = "math_random_int";
    public static String INTERNAL_BLOCK_TYPE_MATH_NUMBER = "math_number";
    public static String INTERNAL_BLOCK_TYPE_MATH_CHANGE = "math_change";
    public static String INTERNAL_BLOCK_TYPE_CONTROLS_WHILE_UNTIL = "controls_whileUntil";
    public static String INTERNAL_BLOCK_TYPE_CONTROLS_REPEAT_EXT = "controls_repeat_ext";
    //public static String INTERNAL_BLOCK_TYPE_CONTROLS_FLOW_STATEMENTS = "controls_flow_statements";
    public static String INTERNAL_BLOCK_TYPE_CONTROLS_IF = "controls_if";
    public static String INTERNAL_BLOCK_TYPE_LOGIC_COMPARE = "logic_compare";

    public static class CategoryID {

        // public final static String ID_BASIC = "basic";
        public final static String ID_CONTROL = "control";
        public final static String ID_DATA_OPERATORS = "data_operations";
        public final static String ID_EVENTS = "events";
        public final static String ID_FILE_OPERATIONS = "file_operations";
        // public final static String ID_MATH = "math";
        public final static String ID_OPERATORS = "operators";
        public final static String ID_RESOURCES = "resources";
        public final static String ID_START_EXIT = "start_exit";
        public final static String ID_VARIABLES = "variables";

        public final static String ID_HTTP = "http";
        public final static String ID_PARSER = "parser";

    }

    String getConfiguration();

    String getBlockType();

    String getCategory();

    String getToolboxConfig();
}
