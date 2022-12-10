import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {Block} from '../shared/models/block.model'
import {ToolboxItem} from '../shared/models/toolbox.model'
import { API_HOST } from '../../environments/environment';

@Injectable()
export class BlocklyService {

  constructor(private http: HttpClient) { }

  getBlockDefinitions() {
    return this.http.get<any>(`${API_HOST}/block-definitions`).pipe(
      map(response => response as Block[])
    );
  }

  getToolbox() {
    return this.http.get<any>(`${API_HOST}/toolbox-config`).pipe(
      map(response => response as ToolboxItem)
    );
  }
}
