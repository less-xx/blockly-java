
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse
} from '@angular/common/http';
import { Router } from '@angular/router';
import { NgEventBus } from 'ng-event-bus';
import { API_HOST } from '../../environments/environment';

export const BASE_URL = `${API_HOST}`;


@Injectable()
export class AppInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
    private eventBus: NgEventBus
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url = req.url;
    url = url.indexOf('http') > -1 ? url : BASE_URL + url;
    console.log(url);
    
    const modifiedReq = req.clone({
      url,
      withCredentials: true
    });
    console.log(url);
    return next.handle(modifiedReq).pipe(
      catchError(this.handleError)
    );
  }

  handleError = (errorResponse: HttpErrorResponse) => {
    return throwError(()=>new Error(errorResponse.error));
  }
}
