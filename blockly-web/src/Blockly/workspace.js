const workspaceXml = `
<xml xmlns="https://developers.google.com/blockly/xml" id="find_all_primes_in_range" >
	<variables>
		<variable id="var_HYOfkg93">primeCount</variable>
		<variable id="var_SMMKzenW">start</variable>
		<variable id="var_hLDCskx3">end</variable>
	</variables>
	<block type="start" id="start_ZBiRmVhj" x="50" y="-710">
		<next>
			<block type="variables_set" id="variables_set_R2ztXMDm">
				<field name="VAR" id="var_HYOfkg93">primeCount</field>
				<value name="VALUE">
					<block type="math_number" id="math_number_UIqJeubY">
						<field name="NUM">0</field>
					</block>
				</value>
				<next>
					<block type="variables_set" id="variables_set_DzVpCReh">
						<field name="VAR" id="var_SMMKzenW">start</field>
						<value name="VALUE">
							<block type="math_number" id="math_number_GWCGHhi9">
								<field name="NUM">1</field>
							</block>
						</value>
						<next>
							<block type="variables_set" id="variables_set_Td32AT7p">
								<field name="VAR" id="var_hLDCskx3">end</field>
								<value name="VALUE">
									<block type="math_number" id="math_number_wKfjB6Lg">
										<field name="NUM">100</field>
									</block>
								</value>
								<next>
									<block type="set_local_variable" id="set_local_variable_885QleJK">
										<field name="var">num</field>
										<value name="value">
											<block type="variables_get" id="variables_get_tm68guCg">
												<field name="VAR" id="var_SMMKzenW">start</field>
											</block>
										</value>
										<next>
											<block type="controls_whileUntil" id="controls_whileUntil_wmzGsdTg">
												<field name="MODE">WHILE</field>
												<value name="BOOL">
													<block type="logic_compare" id="logic_compare_Q0irB1Qi">
														<field name="OP">LT</field>
														<value name="A">
															<block type="get_local_variable" id="get_local_variable_9vh0wq1e">
																<field name="var">num</field>
															</block>
														</value>
														<value name="B">
															<block type="variables_get" id="variables_get_6Gn9eQZg">
																<field name="VAR" id="var_hLDCskx3">end</field>
															</block>
														</value>
													</block>
												</value>
												<statement name="DO">
													<block type="set_local_variable" id="set_local_variable_OxLHeXJe">
														<field name="var">isPrime</field>
														<value name="value">
															<block type="math_number" id="math_number_hiePE3VE">
																<field name="NUM">1</field>
															</block>
														</value>
														<next>
															<block type="set_local_variable" id="set_local_variable_L6ZM8ZpB">
																<field name="var">m</field>
																<value name="value">
																	<block type="math_round" id="math_round_KC7dUuJo">
																		<field name="OP">ROUND</field>
																		<value name="NUM">
																			<block type="math_arithmetic" id="math_arithmetic_S1g1RifC">
																				<field name="OP">DIVIDE</field>
																				<value name="A">
																					<block type="get_local_variable" id="get_local_variable_b8ON4uQo">
																						<field name="var">num</field>
																					</block>
																				</value>
																				<value name="B">
																					<block type="math_number" id="math_number_cZlrw95p">
																						<field name="NUM">2</field>
																					</block>
																				</value>
																			</block>
																		</value>
																	</block>
																</value>
																<next>
																	<block type="set_local_variable" id="set_local_variable_3CYlNAr8">
																		<field name="var">d</field>
																		<value name="value">
																			<block type="math_number" id="math_number_YUOjeZ7Z">
																				<field name="NUM">2</field>
																			</block>
																		</value>
																		<next>
																			<block type="controls_whileUntil" id="controls_whileUntil_rcR1d2Co">
																				<field name="MODE">WHILE</field>
																				<value name="BOOL">
																					<block type="logic_compare" id="logic_compare_v1gnOR37">
																						<field name="OP">LTE</field>
																						<value name="A">
																							<block type="get_local_variable" id="get_local_variable_KU3SXPke">
																								<field name="var">d</field>
																							</block>
																						</value>
																						<value name="B">
																							<block type="get_local_variable" id="get_local_variable_g0Rp3wdA">
																								<field name="var">m</field>
																							</block>
																						</value>
																					</block>
																				</value>
																				<statement name="DO">
																					<block type="controls_if" id="controls_if_dKy0RruU">
																						<value name="IF0">
																							<block type="logic_compare" id="logic_compare_RmpRh7Vd">
																								<field name="OP">EQ</field>
																								<value name="A">
																									<block type="math_arithmetic" id="math_arithmetic_URgi3Gjy">
																										<field name="OP">MOD</field>
																										<value name="A">
																											<block type="get_local_variable" id="get_local_variable_UqFdyz3q">
																												<field name="var">num</field>
																											</block>
																										</value>
																										<value name="B">
																											<block type="get_local_variable" id="get_local_variable_z9PCeWET">
																												<field name="var">d</field>
																											</block>
																										</value>
																									</block>
																								</value>
																								<value name="B">
																									<block type="math_number" id="math_number_w9yuQqNa">
																										<field name="NUM">0</field>
																									</block>
																								</value>
																							</block>
																						</value>
																						<statement name="DO0">
																							<block type="set_local_variable" id="set_local_variable_Jm3wsaO2">
																								<field name="var">isPrime</field>
																								<value name="value">
																									<block type="math_number" id="math_number_Lie92MrT">
																										<field name="NUM">0</field>
																									</block>
																								</value>
																								<next>
																									<block type="controls_flow_statements" id="controls_flow_statements_wvkfadz8">
																										<field name="FLOW">BREAK</field>
																									</block>
																								</next>
																							</block>
																						</statement>
																						<next>
																							<block type="set_local_variable" id="set_local_variable_Iaz4TK9B">
																								<field name="var">d</field>
																								<value name="value">
																									<block type="math_arithmetic" id="math_arithmetic_GHJUckBx">
																										<field name="OP">ADD</field>
																										<value name="A">
																											<block type="get_local_variable" id="get_local_variable_8XrR5B0P">
																												<field name="var">d</field>
																											</block>
																										</value>
																										<value name="B">
																											<block type="math_number" id="math_number_DgoHiZwM">
																												<field name="NUM">1</field>
																											</block>
																										</value>
																									</block>
																								</value>
																							</block>
																						</next>
																					</block>
																				</statement>
																				<next>
																					<block type="controls_if" id="controls_if_GR8F624h">
																						<value name="IF0">
																							<block type="logic_compare" id="logic_compare_8JAAuj2R">
																								<field name="OP">EQ</field>
																								<value name="A">
																									<block type="get_local_variable" id="get_local_variable_XJ5zZhnS">
																										<field name="var">isPrime</field>
																									</block>
																								</value>
																								<value name="B">
																									<block type="math_number" id="math_number_xVAD2hhh">
																										<field name="NUM">1</field>
																									</block>
																								</value>
																							</block>
																						</value>
																						<statement name="DO0">
																							<block type="text_print" id="text_print_tE9N6oJu">
																								<value name="TEXT">
																									<block type="text_join" id="text_join_P7owjRXq">
																										<mutation items="2"></mutation>
																										<value name="ADD0">
																											<block type="get_local_variable" id="get_local_variable_pwzz0cfd">
																												<field name="var">num</field>
																											</block>
																										</value>
																										<value name="ADD1">
																											<block type="text" id="text_D2ysS1Jq">
																												<field name="TEXT"> is a prime number.</field>
																											</block>
																										</value>
																									</block>
																								</value>
																								<next>
																									<block type="variables_set" id="variables_set_rCGUBscD">
																										<field name="VAR" id="var_HYOfkg93">primeCount</field>
																										<value name="VALUE">
																											<block type="math_arithmetic" id="math_arithmetic_EKTCJcTh">
																												<field name="OP">ADD</field>
																												<value name="A">
																													<block type="variables_get" id="variables_get_zro6FBhI">
																														<field name="VAR" id="var_HYOfkg93">primeCount</field>
																													</block>
																												</value>
																												<value name="B">
																													<block type="math_number" id="math_number_SKhQLHec">
																														<field name="NUM">1</field>
																													</block>
																												</value>
																											</block>
																										</value>
																									</block>
																								</next>
																							</block>
																						</statement>
																						<next>
																							<block type="set_local_variable" id="set_local_variable_cNNRUguP">
																								<field name="var">num</field>
																								<value name="value">
																									<block type="math_arithmetic" id="math_arithmetic_nihk2U1U">
																										<field name="OP">ADD</field>
																										<value name="A">
																											<block type="get_local_variable" id="get_local_variable_s2tkeRWA">
																												<field name="var">num</field>
																											</block>
																										</value>
																										<value name="B">
																											<block type="math_number" id="math_number_M0QkYbLC">
																												<field name="NUM">1</field>
																											</block>
																										</value>
																									</block>
																								</value>
																							</block>
																						</next>
																					</block>
																				</next>
																			</block>
																		</next>
																	</block>
																</next>
															</block>
														</next>
													</block>
												</statement>
												<next>
													<block type="text_print" id="text_print_LAzIsZM7">
														<value name="TEXT">
															<block type="text_join" id="text_join_iVkygg8E">
																<mutation items="5"></mutation>
																<value name="ADD0">
																	<block type="text" id="text_5e5D64qm">
																		<field name="TEXT">There're </field>
																	</block>
																</value>
																<value name="ADD1">
																	<block type="variables_get" id="variables_get_L4m7Wb8Z">
																		<field name="VAR" id="var_HYOfkg93">primeCount</field>
																	</block>
																</value>
																<value name="ADD2">
																	<block type="text" id="text_GxB5jBiE">
																		<field name="TEXT"> primes between </field>
																	</block>
																</value>
																<value name="ADD3">
																	<block type="variables_get" id="variables_get_1Xi5ttWX">
																		<field name="VAR" id="var_SMMKzenW">start</field>
																	</block>
																</value>
																<value name="ADD4">
																	<block type="variables_get" id="variables_get_qyScRWRu">
																		<field name="VAR" id="var_hLDCskx3">end</field>
																	</block>
																</value>
															</block>
														</value>
													</block>
												</next>
											</block>
										</next>
									</block>
								</next>
							</block>
						</next>
					</block>
				</next>
			</block>
		</next>
	</block>
</xml>
`
export default workspaceXml;