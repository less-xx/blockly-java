/**
 * @license
 *
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Blockly React Component.
 * @author samelh@google.com (Sam El-Husseini)
 */

import React from 'react';
import './BlocklyComponent.css';

import Blockly from 'blockly/core';
import locale from 'blockly/msg/en';
import 'blockly/blocks';
import toolboxConfigXml from './toolbox';
import customBlockTemplate from './CustomBlockTemplate.json'
import './CustomBlockTheme'
import BlocklyTheme from './CustomBlockTheme';
import BlocklyService from '../Service/BlockService'

Blockly.setLocale(locale);

class BlocklyComponent extends React.Component {
    constructor(props) {
        super(props);
        this.blocklyDiv = React.createRef();
        this.toolbox = React.createRef();
    }

    componentDidMount() {

        const { initialXml, children, ...rest } = this.props;

        BlocklyService.fetchBlockDefinitions((blockDefs)=>{
            blockDefs.forEach(bt=>{
                this.registerBlock(bt.type, bt.definition);
            })
        })

        //var toolboxConfig = this.getToolbox();
       
        BlocklyService.fetchToolbox((toolbox)=>{
            this.primaryWorkspace = Blockly.inject(
                this.blocklyDiv.current,
                {
                    toolbox: toolbox,
                    theme: BlocklyTheme,
                    ...rest
                },
            );
            this.primaryWorkspace.addChangeListener(e => this.onWorkspaceChange(e));

            if (initialXml) {
                Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom(initialXml), this.primaryWorkspace);
            }
        })

    }

    get workspace() {
        return this.primaryWorkspace;
    }

    onWorkspaceChange (e, workspace) {

        if (e instanceof Blockly.Events.Ui) {
            return;
        }

        var xmlStr = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(this.primaryWorkspace));

        console.log(xmlStr)
    }

    getToolbox() {
        return (new window.DOMParser()).parseFromString(toolboxConfigXml, "text/xml");
    }

    setXml(xml) {
        Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom(xml), this.primaryWorkspace);
    }

    registerBlock (blockType, blockDef) {
        //console.log(blockDef);
        Blockly.Blocks[blockType] = {
            init: function () {
                this.jsonInit(blockDef);
                if (blockDef.hat) {
                    this.hat = blockDef.hat;
                }
            }
        };
    }

    render() {
        
        return <React.Fragment>
            <div ref={this.blocklyDiv} id="blocklyDiv" />
        </React.Fragment>;
    }
}

export default BlocklyComponent;
