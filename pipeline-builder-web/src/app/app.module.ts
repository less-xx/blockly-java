import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppInterceptor } from './shared/http-interceptor.service';
import { AppComponent } from './app.component';
import { BlocklyComponent } from './blockly/blockly.component';
import { BlocklyService } from './blockly/blockly.service';
import { EventBus } from './shared/event-bus.service';

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
  ],
  providers: [
    //httpInterceptorProviders,
    BlocklyService,
    EventBus
  ],
  bootstrap: [AppComponent],
  exports: [BlocklyComponent]
})
export class AppModule { }
