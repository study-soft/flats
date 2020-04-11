import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  user: any;
  submitted: false;

  constructor() { }

  ngOnInit(): void {
    this.user = {
      username: "hello",
      password: "world"
    }
  }

  login() {

  }
}
