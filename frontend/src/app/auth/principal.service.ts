import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from "rxjs";
import { User } from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class PrincipalService {

  currentUser$: Observable<User>;

  private currentUserSubject: BehaviorSubject<User>;

  constructor() {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(sessionStorage.getItem("currentUser") || localStorage.getItem("currentUser"))
    );
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  getCurrentUser(): User {
    return this.currentUserSubject.value;
  }

  storeUser(user: User, rememberMe?: boolean): void {
    if (rememberMe) {
      localStorage.setItem("currentUser", JSON.stringify(user));
    } else {
      sessionStorage.setItem("currentUser", JSON.stringify(user));
    }
    this.currentUserSubject.next(user);
  }

  removeUser(): void {
    localStorage.removeItem("currentUser");
    sessionStorage.removeItem("currentUser");
    this.currentUserSubject.next(null);
  }
}
