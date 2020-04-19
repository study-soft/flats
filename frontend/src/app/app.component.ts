import { Component, OnDestroy, OnInit } from '@angular/core';
import { PrincipalService } from './auth/services/principal.service';
import { Subscription } from 'rxjs';
import { AuthService } from './auth/services/auth.service';
import { Router } from '@angular/router';
import { User } from './auth/models/user.model';
import { NbThemeService } from '@nebular/theme';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'frontend';

  currentUser: User;
  private currentUserSubscription: Subscription;

  constructor(private principalService: PrincipalService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.currentUserSubscription = this.principalService.currentUser$
      .subscribe(user => {
        console.log('Current user changes: ', user);
        this.currentUser = user;
      });
  }

  ngOnDestroy(): void {
    this.currentUserSubscription.unsubscribe();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
