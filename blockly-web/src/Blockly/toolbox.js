const toolboxConfigXml = `
<xml xmlns="https://developers.google.com/blockly/xml" is="blockly" style="display: 'none'" id="toolboxConfig">
    <category name="Variables" categorystyle="variables">
        <block type="variables_get" />
        <block type="variables_set" />
<!--
        <block type="set_local_variable" />
        <block type="get_local_variable" />
-->
    </category>
    <category name="Events" categorystyle="events">
<!--        <block type="get_event_param" />
        <block type="handle_event" />
        <block type="event_with_param" />
        <block type="dispatch_event" />
-->
    </category>
    <category name="Start | Stop" categorystyle="start_stop">
 <!--
        <block type="exit" />
        <block type="start" />
-->
    </category>
    <category name="Basic" categorystyle="basic">
        <category name="Math" categorystyle="math">
            <block type="math_random_int" />
            <block type="math_number" />
            <block type="math_round" />
            <block type="math_arithmetic" />
        </category>
        <category name="Text" categorystyle="test">
            <block type="text_join" />
            <block type="text" />
            <block type="text_print" />
        </category>
        <category name="Control" categorystyle="control">
<!--            <block type="wait_seconds" /> -->
            <block type="controls_if" />
            <block type="logic_compare" />
            <block type="controls_flow_statements" />
            <block type="controls_whileUntil" />
            <block type="controls_repeat_ext" />
        </category>
    </category>
</xml>
`
export default toolboxConfigXml;