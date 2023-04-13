import { Component,ViewChild, TemplateRef, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import { NgEventBus } from 'ng-event-bus';
import {Event} from './shared/models/event.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  
  workspaceConfig: string = '';
  @ViewChild('workspaceConfDialog', {static: true}) workspaceConfDialog: TemplateRef<any>;

  private dialogRef: MatDialogRef<any> =  null;

  constructor(
    private dialog: MatDialog,
    private eventBus: NgEventBus
  ) {}

  showWorkspaceConfig(): void {
    this.dialogRef = this.dialog.open(this.workspaceConfDialog, {
      width: '800px',
      height: '80%'
    });

  }

  onClose() {
    //console.log('Cancel clicked, '+this.workspaceConfig);
    if(this.dialogRef) {
      this.dialogRef.close();
    } 
  }

  ngOnInit() {
    this.eventBus.on(Event.EVENT_WORKSPACE_CONFIG_UPDATED).subscribe((data: any) => {
      //console.log(data);
      this.workspaceConfig = JSON.stringify(data, null, 4);
    });
  }
}
