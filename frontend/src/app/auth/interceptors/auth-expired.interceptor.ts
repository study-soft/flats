import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from "rxjs/operators";
import { AuthService } from "../services/auth.service";

@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    return next.handle(request).pipe(
      tap(() => {},
        err => {
          if (err instanceof HttpErrorResponse && err.status === 402) {
            this.authenticationService.logout();
          }
        })
    );
  }
}
