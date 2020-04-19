import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from "../services/auth.service";
import { PrincipalService } from "../services/principal.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthService,
              private principalService: PrincipalService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (this.principalService.getCurrentUser()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.principalService.getCurrentUser().token}`
        }
      });
    }

    return next.handle(request);
  }
}
