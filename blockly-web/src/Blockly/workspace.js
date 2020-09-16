const workspaceXml = `
<xml xmlns="https://developers.google.com/blockly/xml" style="display: none">
	<block type="start" id="start_ZBiRmVhj" x="50" y="-1310">
		<next>
			<block type="watch_file_system" id="watch_file_system_VnOWRZL8">
				<field name="FILE_RESOURCE">test-user-file-resource1</field>
				<field name="EVENT">fileCreateUpdateDeleteEvents</field>
			</block>
		</next>
	</block>
	<block type="handle_event" id="handle_event_nx12ZCul" x="490" y="-1310">
		<next>
			<block type="text_print" id="text_print_LrrpBwXy">
				<value name="TEXT">
					<block type="get_object_property" id="get_object_property_5HqPOLu0">
						<value name="OBJECT">
							<block type="get_event_param" id="get_event_param_ykjPj1ss">
								<field name="event_name">fileCreateUpdateDeleteEvents</field>
							</block>
						</value>
						<field name="PROPERTY_NAME">event</field>
					</block>
				</value>
			</block>
		</next>
		<field name="event_name">fileCreateUpdateDeleteEvents</field>
	</block>
</xml>

`
export default workspaceXml;