import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../services/auth.service";
import { first } from "rxjs/operators";
import { ActivatedRoute, Router } from "@angular/router";
import { NbToastrService } from "@nebular/theme";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  returnUrl: string;

  rememberMe = false;

  loading = false;
  submitted = false;

  loginForm: FormGroup;
  username: FormControl;
  password: FormControl;

  constructor(private authenticationService: AuthService,
              private router: Router,
              private route: ActivatedRoute,
              private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    this.username = new FormControl("", Validators.required);
    this.password = new FormControl("", Validators.required);
    this.loginForm = new FormGroup({
      username: this.username,
      password: this.password
    });

    this.returnUrl = this.route.snapshot.queryParams.returnUrl || "/";
  }

  login(): void {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.username.value, this.password.value, this.rememberMe)
      .pipe(first())
      .subscribe(
        user => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.toastrService.danger(error.error.message, "Authentication error");
          this.loading = false;
        }
      );
  }

  setRememberMe(value: boolean): void {
    this.rememberMe = value;
  }
}
