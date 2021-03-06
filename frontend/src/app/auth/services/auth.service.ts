import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from "rxjs";
import { User } from "../models/user.model";
import { HttpClient } from "@angular/common/http";
import { tap } from "rxjs/operators";
import { environment } from "../../../environments/environment";
import { PrincipalService } from "./principal.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly BASE_URL = environment.serverApiUrl;

  constructor(private http: HttpClient,
              private principalService: PrincipalService) {
  }

  login(username: string, password: string, rememberMe: boolean): Observable<User> {
    return this.http.post<User>(`${this.BASE_URL}/users/authenticate`,
      {username, password}
    ).pipe(
      tap(user => {
        this.principalService.storeUser(user, rememberMe);
      })
    );
  }

  logout(): void {
    this.principalService.removeUser();
  }
}
