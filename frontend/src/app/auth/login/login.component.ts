import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthenticationService } from "../authentication.service";
import { first } from "rxjs/operators";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  returnUrl: string;

  loading: boolean = false;
  submitted: boolean = false;

  loginForm: FormGroup;
  username: FormControl;
  password: FormControl;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.username = new FormControl("", Validators.required);
    this.password = new FormControl("", Validators.required);
    this.loginForm = new FormGroup({
      username: this.username,
      password: this.password
    });

    this.returnUrl = this.route.snapshot.queryParams["returnUrl"] || "/";
  }

  login(): void {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.username.value, this.password.value)
      .pipe(first())
      .subscribe(
        user => {
          console.log(user);
          this.router.navigate([this.returnUrl])
        },
        error => {
          this.loading = false;
          console.log(error)
        }
      )
  }
}
