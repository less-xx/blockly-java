import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {Block} from '../shared/models/block.model'
import {ToolboxItem} from '../shared/models/toolbox.model'
import { API_HOST, FRONTEND_ONLY } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable()
export class BlocklyService {

  constructor(private http: HttpClient) { }

  getBlockDefinitions(): Observable<Block[]> {
    if(FRONTEND_ONLY){
      return this.http.get<Block[]>(`/assets/resources/default-block-definitions.json`).pipe(
        map(response => response as Block[])
      ); 
    }
    return this.http.get<Block[]>(`${API_HOST}/block-definitions`).pipe(
      map(response => response as Block[])
    );
  }

  getToolbox() : Observable<ToolboxItem> {
    if(FRONTEND_ONLY){
      return this.http.get<ToolboxItem>(`/assets/resources/default-toolbox-config.json`).pipe(
        map(response => response as ToolboxItem)
      ); 
    }
    return this.http.get<ToolboxItem>(`${API_HOST}/toolbox-config`).pipe(
      map(response => response as ToolboxItem)
    );
  }

  saveWorkspace() {
    //this.http.
  }
}
