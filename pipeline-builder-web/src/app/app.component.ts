import { Component,ViewChild, TemplateRef, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import { MetaData, NgEventBus } from 'ng-event-bus';
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

  updateWorkspaceConfig() {
    this.eventBus.cast(Event.EVENT_WORKSPACE_CONFIG_UPDATED_BY_CODE, JSON.parse(this.workspaceConfig));
    this.onClose();
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];

    if (file) {
      const fileReader = new FileReader();
      fileReader.onload = (e) => {
        try {
          const jsonData = JSON.parse(fileReader.result as string);
          this.eventBus.cast(Event.EVENT_WORKSPACE_CONFIG_UPDATED_BY_CODE, jsonData);
        } catch (error) {
          console.error('Error parsing JSON:', error);
          alert('Error parsing JSON. Please check the console for more details.');
        }
      };
      fileReader.readAsText(file);
    }
  }

  ngOnInit() {
    this.eventBus.on(Event.EVENT_WORKSPACE_CONFIG_UPDATED_BY_EDITOR).subscribe((meta: MetaData) => {
      //console.log(data);
      this.workspaceConfig = JSON.stringify(meta.data, null, 4);
    });
  }
}
