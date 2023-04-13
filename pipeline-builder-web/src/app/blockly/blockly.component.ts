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
 * @fileoverview Blockly Angular Component.
 * @author samelh@google.com (Sam El-Husseini)
 */

import { Component, OnInit } from '@angular/core';
import { MetaData, NgEventBus } from 'ng-event-bus';  

import * as Blockly from 'blockly';
import { BlocklyOptions } from 'blockly';
import { BlocklyService } from './blockly.service';
import {Block} from '../shared/models/block.model';
import {Event} from '../shared/models/event.model';
import {CustomTheme } from './blockly.theme';
import {ITheme} from 'blockly/core/theme';

@Component({
  selector: 'app-blockly',
  templateUrl: './blockly.component.html',
  styleUrls: ['./blockly.component.css']
})
export class BlocklyComponent implements OnInit {

  workspace: Blockly.Workspace;

  constructor(
    private blocklyService: BlocklyService,
    private eventBus: NgEventBus
  ) { }

  ngOnInit() {
    const blocklyDiv = document.getElementById('blocklyDiv');
    this.eventBus.on(Event.EVENT_WORKSPACE_RENDERED).subscribe((meta: MetaData) => {
      console.log("Workspace rendered");
      this.workspace.addChangeListener((event, evtBus)=>{
        if(event.type === Blockly.Events.BLOCK_CREATE || 
          event.type === Blockly.Events.BLOCK_CHANGE||
          event.type === Blockly.Events.BLOCK_DELETE||
          event.type === Blockly.Events.BLOCK_MOVE){
            this.eventBus.cast(Event.EVENT_WORKSPACE_UPDATED, event);
        }
      });
    });
    
    this.eventBus.on(Event.EVENT_WORKSPACE_UPDATED) .subscribe((meta: MetaData) => {
      console.log("Workspace updated");
      let json = Blockly.serialization.workspaces.save(this.workspace);
      this.eventBus.cast(Event.EVENT_WORKSPACE_CONFIG_UPDATED, json);
    });

    this.blocklyService.getBlockDefinitions().subscribe((blockDefs: Block[])=>{
      blockDefs.forEach((bdef)=>{
        //console.log(bdef);
        Blockly.Blocks[bdef.type] = {
          init: function() {
            this.jsonInit(bdef);
          }
        }
      });
    });

    const theme = Blockly.Theme.defineTheme(CustomTheme.name, CustomTheme);
    console.log(theme);
    this.blocklyService.getToolbox().subscribe((toolbox: any)=>{
      console.log(toolbox);
      this.workspace = Blockly.inject(blocklyDiv, {
        readOnly: false,
        media: 'media/',
        trashcan: true,
        move: {
          scrollbars: true,
          drag: true,
          wheel: true
        },
        grid: {
          spacing: 20,
          length: 3,
          colour: '#ccc',
          snap: true
        },
        theme:  theme,
        toolbox: toolbox
      } as BlocklyOptions);
      this.eventBus.cast(Event.EVENT_WORKSPACE_RENDERED, this.workspace);
    });
  }
  
}
