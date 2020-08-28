const workspaceXml = `
<xml xmlns="https://developers.google.com/blockly/xml" style="display: none">
	<block type="start" id="start_ZBiRmVhj" x="230" y="30">
		<next>
			<block type="set_local_variable" id="set_local_variable_885QleJK">
				<next>
					<block type="controls_whileUntil" id="controls_whileUntil_wmzGsdTg">
						<next>
							<block type="text_print" id="text_print_LAzIsZM7">
								<value name="TEXT">
									<block type="text_join" id="text_join_iVkygg8E">
										<mutation items="2"/>
										<value name="ADD0">
											<block type="text" id="text_5e5D64qm">
												<field name="TEXT">x=</field>
											</block>
										</value>
										<value name="ADD1">
											<block type="get_local_variable" id="get_local_variable_tqv1Q4nc">
												<field name="var">x</field>
											</block>
										</value>
									</block>
								</value>
							</block>
						</next>
						<statement name="DO">
							<block type="controls_if" id="controls_if_dKy0RruU">
								<next>
									<block type="controls_if" id="controls_if_GR8F624h">
										<next>
											<block type="set_local_variable" id="set_local_variable_3CYlNAr8">
												<value name="value">
													<block type="math_arithmetic" id="math_arithmetic_GHJUckBx">
														<value name="A">
															<block type="get_local_variable" id="get_local_variable_KU3SXPke">
																<field name="var">x</field>
															</block>
														</value>
														<value name="B">
															<block type="math_number" id="math_number_DgoHiZwM">
																<field name="NUM">1</field>
															</block>
														</value>
														<field name="OP">MINUS</field>
													</block>
												</value>
												<field name="var">x</field>
											</block>
										</next>
										<statement name="DO0">
											<block type="controls_flow_statements" id="controls_flow_statements_Rmdexaq5">
												<field name="FLOW">BREAK</field>
											</block>
										</statement>
										<value name="IF0">
											<block type="logic_compare" id="logic_compare_L15VVzMw">
												<value name="A">
													<block type="get_local_variable" id="get_local_variable_T8Pms5AD">
														<field name="var">x</field>
													</block>
												</value>
												<value name="B">
													<block type="math_number" id="math_number_qPhpJAHD">
														<field name="NUM">1</field>
													</block>
												</value>
												<field name="OP">EQ</field>
											</block>
										</value>
									</block>
								</next>
								<statement name="DO0">
									<block type="text_print" id="text_print_tE9N6oJu">
										<value name="TEXT">
											<block type="text_join" id="text_join_P7owjRXq">
												<mutation items="2"/>
												<value name="ADD0">
													<block type="text" id="text_D2ysS1Jq">
														<field name="TEXT">x=</field>
													</block>
												</value>
												<value name="ADD1">
													<block type="get_local_variable" id="get_local_variable_b8ON4uQo">
														<field name="var">x</field>
													</block>
												</value>
											</block>
										</value>
									</block>
								</statement>
								<value name="IF0">
									<block type="logic_compare" id="logic_compare_v1gnOR37">
										<value name="A">
											<block type="math_arithmetic" id="math_arithmetic_S1g1RifC">
												<value name="A">
													<block type="get_local_variable" id="get_local_variable_g0Rp3wdA">
														<field name="var">x</field>
													</block>
												</value>
												<value name="B">
													<block type="math_number" id="math_number_cZlrw95p">
														<field name="NUM">2</field>
													</block>
												</value>
												<field name="OP">MOD</field>
											</block>
										</value>
										<value name="B">
											<block type="math_number" id="math_number_UIqJeubY">
												<field name="NUM">0</field>
											</block>
										</value>
										<field name="OP">EQ</field>
									</block>
								</value>
							</block>
						</statement>
						<value name="BOOL">
							<block type="logic_compare" id="logic_compare_Q0irB1Qi">
								<value name="A">
									<block type="get_local_variable" id="get_local_variable_9vh0wq1e">
										<field name="var">x</field>
									</block>
								</value>
								<value name="B">
									<block type="math_number" id="math_number_hiePE3VE">
										<field name="NUM">0</field>
									</block>
								</value>
								<field name="OP">GT</field>
							</block>
						</value>
						<field name="MODE">WHILE</field>
					</block>
				</next>
				<value name="value">
					<block type="math_number" id="math_number_YUOjeZ7Z">
						<field name="NUM">10</field>
					</block>
				</value>
				<field name="var">x</field>
			</block>
		</next>
	</block>
</xml>
`
export default workspaceXml;