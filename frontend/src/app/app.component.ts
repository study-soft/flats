import { Component, OnDestroy, OnInit } from '@angular/core';
import { PrincipalService } from "./auth/principal.service";
import { Subscription } from "rxjs";
import { AuthService } from "./auth/auth.service";
import { Router } from "@angular/router";
import { User } from "./auth/user.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'frontend';

  currentUser: User;
  private subscription: Subscription;

  constructor(private principalService: PrincipalService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.subscription = this.principalService.currentUser$
      .subscribe(user => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
