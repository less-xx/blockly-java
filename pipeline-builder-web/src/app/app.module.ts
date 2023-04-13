import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppInterceptor } from './shared/http-interceptor.service';
import { AppComponent } from './app.component';
import { BlocklyComponent } from './blockly/blockly.component';
import { BlocklyService } from './blockly/blockly.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import {MatDialogModule} from "@angular/material/dialog";
import { FormsModule } from '@angular/forms'; 
import {MatFormFieldModule} from "@angular/material/form-field";
import { NgEventBus } from 'ng-event-bus';

/** Http interceptor providers in outside-in order */
// export const httpInterceptorProviders = [
//   { provide: HTTP_INTERCEPTORS, useClass: AppInterceptor, multi: true },
// ];

@NgModule({
  declarations: [
    AppComponent,
    BlocklyComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule
  ],
  providers: [
    //httpInterceptorProviders,
    BlocklyService,
    NgEventBus
  ],
  bootstrap: [AppComponent],
  exports: [BlocklyComponent]
})
export class AppModule { }
