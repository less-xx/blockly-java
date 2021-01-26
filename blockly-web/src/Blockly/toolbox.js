const toolboxConfigXml = `
<xml xmlns="https://developers.google.com/blockly/xml" style="display: none">
	<category name="Start | Exit" categorystyle="start_exit">
		<block type="exit"/>
		<block type="start"/>
	</category>
	<category name="Basic" categorystyle="basic">
		<block type="get_object_property"/>
		<block type="text_to_json"/>
		<block type="text_print"/>
		<block type="text_join"/>
		<block type="text"/>
	</category>
	<category name="Variables" categorystyle="variables">
		<block type="variables_get"/>
		<block type="set_local_variable"/>
		<block type="variables_set"/>
		<block type="get_local_variable"/>
	</category>
	<category name="Control" categorystyle="control">
		<block type="wait_seconds">
			<value name="value">
				<shadow type="math_number">
					<field name="NUM">5</field>
				</shadow>
			</value>
		</block>
		<block type="controls_flow_statements"/>
		<block type="controls_whileUntil"/>
		<block type="foreach_in_collection"/>
		<block type="controls_if"/>
		<block type="logic_compare"/>
		<block type="controls_repeat_ext"/>
	</category>
	<category name="Events" categorystyle="events">
		<block type="get_event_param"/>
		<block type="handle_event"/>
		<block type="event_with_param"/>
		<block type="dispatch_event">
			<value name="event">
				<shadow type="event_with_param">
					<field name="event_name">event1</field>
				</shadow>
			</value>
		</block>
	</category>
	<category name="File Operations" categorystyle="file_operations">
		<block type="watch_file_system"/>
	</category>
	<category name="Math" categorystyle="math">
		<block type="math_number"/>
		<block type="math_round"/>
		<block type="math_random_int"/>
		<block type="math_arithmetic"/>
	</category>
</xml>
`
export default toolboxConfigXml;