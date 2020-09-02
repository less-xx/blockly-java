const workspaceXml = `
<xml xmlns="https://developers.google.com/blockly/xml" style="display: none" id="test_workspace_exec_05">
	<variables>
		<variable id="var_HYOfkg93">primeCount</variable>
		<variable id="var_SMMKzenW">start</variable>
		<variable id="var_hLDCskx3">end</variable>
	</variables>
	<block type="start" id="start_ZBiRmVhj" x="50" y="-1310">
		<next>
			<block type="variables_set" id="variables_set_R2ztXMDm">
				<next>
					<block type="variables_set" id="variables_set_DzVpCReh">
						<next>
							<block type="variables_set" id="variables_set_Td32AT7p">
								<next>
									<block type="set_local_variable" id="set_local_variable_885QleJK">
										<next>
											<block type="controls_whileUntil" id="controls_whileUntil_wmzGsdTg">
												<next>
													<block type="text_print" id="text_print_LAzIsZM7">
														<value name="TEXT">
															<block type="text_join" id="text_join_iVkygg8E">
																<mutation items="6" />
																<value name="ADD0">
																	<block type="text" id="text_5e5D64qm">
																		<field name="TEXT">There're </field>
																	</block>
																</value>
																<value name="ADD1">
																	<block type="variables_get" id="variables_get_L4m7Wb8Z">
																		<field id="var_HYOfkg93" name="VAR">primeCount</field>
																	</block>
																</value>
																<value name="ADD2">
																	<block type="text" id="text_GxB5jBiE">
																		<field name="TEXT"> primes between </field>
																	</block>
																</value>
																<value name="ADD3">
																	<block type="variables_get" id="variables_get_1Xi5ttWX">
																		<field id="var_SMMKzenW" name="VAR">start</field>
																	</block>
																</value>
																<value name="ADD4">
																	<block type="text" id="text_Qgb5fAU8">
																		<field name="TEXT"> and </field>
																	</block>
																</value>
																<value name="ADD5">
																	<block type="variables_get" id="variables_get_qyScRWRu">
																		<field id="var_hLDCskx3" name="VAR">end</field>
																	</block>
																</value>
															</block>
														</value>
													</block>
												</next>
												<value name="BOOL">
													<block type="logic_compare" id="logic_compare_Q0irB1Qi">
														<value name="A">
															<block type="get_local_variable" id="get_local_variable_9vh0wq1e">
																<field name="var">num</field>
															</block>
														</value>
														<value name="B">
															<block type="variables_get" id="variables_get_6Gn9eQZg">
																<field id="var_hLDCskx3" name="VAR">end</field>
															</block>
														</value>
														<field name="OP">LT</field>
													</block>
												</value>
												<statement name="DO">
													<block type="controls_if" id="controls_if_2kHyeIyW">
														<mutation elseif="1" />
														<next>
															<block type="set_local_variable" id="set_local_variable_OxLHeXJe">
																<next>
																	<block type="set_local_variable" id="set_local_variable_L6ZM8ZpB">
																		<next>
																			<block type="set_local_variable" id="set_local_variable_3CYlNAr8">
																				<next>
																					<block type="controls_whileUntil" id="controls_whileUntil_rcR1d2Co">
																						<next>
																							<block type="controls_if" id="controls_if_GR8F624h">
																								<next>
																									<block type="set_local_variable" id="set_local_variable_cNNRUguP">
																										<value name="value">
																											<block type="math_arithmetic" id="math_arithmetic_nihk2U1U">
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
																												<field name="OP">ADD</field>
																											</block>
																										</value>
																										<field name="var">num</field>
																									</block>
																								</next>
																								<value name="IF0">
																									<block type="logic_compare" id="logic_compare_8JAAuj2R">
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
																										<field name="OP">EQ</field>
																									</block>
																								</value>
																								<statement name="DO0">
																									<block type="text_print" id="text_print_tE9N6oJu">
																										<next>
																											<block type="variables_set" id="variables_set_rCGUBscD">
																												<value name="VALUE">
																													<block type="math_arithmetic" id="math_arithmetic_EKTCJcTh">
																														<value name="A">
																															<block type="variables_get" id="variables_get_zro6FBhI">
																																<field id="var_HYOfkg93" name="VAR">primeCount</field>
																															</block>
																														</value>
																														<value name="B">
																															<block type="math_number" id="math_number_SKhQLHec">
																																<field name="NUM">1</field>
																															</block>
																														</value>
																														<field name="OP">ADD</field>
																													</block>
																												</value>
																												<field id="var_HYOfkg93" name="VAR">primeCount</field>
																											</block>
																										</next>
																										<value name="TEXT">
																											<block type="text_join" id="text_join_P7owjRXq">
																												<mutation items="2" />
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
																									</block>
																								</statement>
																							</block>
																						</next>
																						<value name="BOOL">
																							<block type="logic_compare" id="logic_compare_v1gnOR37">
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
																								<field name="OP">LTE</field>
																							</block>
																						</value>
																						<statement name="DO">
																							<block type="controls_if" id="controls_if_dKy0RruU">
																								<next>
																									<block type="set_local_variable" id="set_local_variable_Iaz4TK9B">
																										<value name="value">
																											<block type="math_arithmetic" id="math_arithmetic_GHJUckBx">
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
																												<field name="OP">ADD</field>
																											</block>
																										</value>
																										<field name="var">d</field>
																									</block>
																								</next>
																								<value name="IF0">
																									<block type="logic_compare" id="logic_compare_RmpRh7Vd">
																										<value name="A">
																											<block type="math_arithmetic" id="math_arithmetic_URgi3Gjy">
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
																												<field name="OP">MOD</field>
																											</block>
																										</value>
																										<value name="B">
																											<block type="math_number" id="math_number_w9yuQqNa">
																												<field name="NUM">0</field>
																											</block>
																										</value>
																										<field name="OP">EQ</field>
																									</block>
																								</value>
																								<statement name="DO0">
																									<block type="set_local_variable" id="set_local_variable_Jm3wsaO2">
																										<next>
																											<block type="controls_flow_statements" id="controls_flow_statements_wvkfadz8">
																												<field name="FLOW">BREAK</field>
																											</block>
																										</next>
																										<value name="value">
																											<block type="math_number" id="math_number_Lie92MrT">
																												<field name="NUM">0</field>
																											</block>
																										</value>
																										<field name="var">isPrime</field>
																									</block>
																								</statement>
																							</block>
																						</statement>
																						<field name="MODE">WHILE</field>
																					</block>
																				</next>
																				<value name="value">
																					<block type="math_number" id="math_number_YUOjeZ7Z">
																						<field name="NUM">2</field>
																					</block>
																				</value>
																				<field name="var">d</field>
																			</block>
																		</next>
																		<value name="value">
																			<block type="math_round" id="math_round_KC7dUuJo">
																				<value name="NUM">
																					<block type="math_arithmetic" id="math_arithmetic_S1g1RifC">
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
																						<field name="OP">DIVIDE</field>
																					</block>
																				</value>
																				<field name="OP">ROUND</field>
																			</block>
																		</value>
																		<field name="var">m</field>
																	</block>
																</next>
																<value name="value">
																	<block type="math_number" id="math_number_hiePE3VE">
																		<field name="NUM">1</field>
																	</block>
																</value>
																<field name="var">isPrime</field>
															</block>
														</next>
														<value name="IF0">
															<block type="logic_compare" id="logic_compare_Gx4WNusB">
																<value name="A">
																	<block type="get_local_variable" id="get_local_variable_rUWOwlvZ">
																		<field name="var">num</field>
																	</block>
																</value>
																<value name="B">
																	<block type="math_number" id="math_number_A81xZVqd">
																		<field name="NUM">0</field>
																	</block>
																</value>
																<field name="OP">EQ</field>
															</block>
														</value>
														<value name="IF1">
															<block type="logic_compare" id="logic_compare_t1hxriMG">
																<value name="A">
																	<block type="get_local_variable" id="get_local_variable_MdMIO3kZ">
																		<field name="var">num</field>
																	</block>
																</value>
																<value name="B">
																	<block type="math_number" id="math_number_7hlJSnoi">
																		<field name="NUM">1</field>
																	</block>
																</value>
																<field name="OP">EQ</field>
															</block>
														</value>
														<statement name="DO0">
															<block type="set_local_variable" id="set_local_variable_w70Qd5h4">
																<next>
																	<block type="controls_flow_statements" id="controls_flow_statements_UhAcf5vP">
																		<field name="FLOW">CONTINUE</field>
																	</block>
																</next>
																<value name="value">
																	<block type="math_arithmetic" id="math_arithmetic_hjZNkh1T">
																		<value name="A">
																			<block type="get_local_variable" id="get_local_variable_sxfMlfdM">
																				<field name="var">num</field>
																			</block>
																		</value>
																		<value name="B">
																			<block type="math_number" id="math_number_rolQBEI4">
																				<field name="NUM">1</field>
																			</block>
																		</value>
																		<field name="OP">ADD</field>
																	</block>
																</value>
																<field name="var">num</field>
															</block>
														</statement>
														<statement name="DO1">
															<block type="set_local_variable" id="set_local_variable_NiBnKIze">
																<next>
																	<block type="controls_flow_statements" id="controls_flow_statements_RqPUhnMr">
																		<field name="FLOW">CONTINUE</field>
																	</block>
																</next>
																<value name="value">
																	<block type="math_arithmetic" id="math_arithmetic_kney6txE">
																		<value name="A">
																			<block type="get_local_variable" id="get_local_variable_N4XrX2xK">
																				<field name="var">num</field>
																			</block>
																		</value>
																		<value name="B">
																			<block type="math_number" id="math_number_suHoOHQv">
																				<field name="NUM">1</field>
																			</block>
																		</value>
																		<field name="OP">ADD</field>
																	</block>
																</value>
																<field name="var">num</field>
															</block>
														</statement>
													</block>
												</statement>
												<field name="MODE">WHILE</field>
											</block>
										</next>
										<value name="value">
											<block type="variables_get" id="variables_get_tm68guCg">
												<field id="var_SMMKzenW" name="VAR">start</field>
											</block>
										</value>
										<field name="var">num</field>
									</block>
								</next>
								<value name="VALUE">
									<block type="math_number" id="math_number_wKfjB6Lg">
										<field name="NUM">10</field>
									</block>
								</value>
								<field id="field_pAfHecMv" name="VAR">end</field>
							</block>
						</next>
						<value name="VALUE">
							<block type="math_number" id="math_number_GWCGHhi9">
								<field name="NUM">0</field>
							</block>
						</value>
						<field id="field_v3OxYTGs" name="VAR">start</field>
					</block>
				</next>
				<value name="VALUE">
					<block type="math_number" id="math_number_UIqJeubY">
						<field name="NUM">0</field>
					</block>
				</value>
				<field id="field_RIdMpOVM" name="VAR">primeCount</field>
			</block>
		</next>
	</block>
</xml>

`
export default workspaceXml;