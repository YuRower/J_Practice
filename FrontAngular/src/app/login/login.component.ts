import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ApiService} from "../service/api.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalidLogin: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const loginPayload = {
      login: this.loginForm.controls.login.value,
      password: this.loginForm.controls.password.value
    }
    this.apiService.login(loginPayload).subscribe(data => {
      if ( data.status === 200 && data.result.roleId.name === "ADMIN" ) {
                    console.log(data.result);

        window.localStorage.setItem('token', data.result.token);
        this.router.navigate(['user-list'], { queryParams: { username: data.result.username }});
      } else if( data.status === 200 && data.result.roleId.name === "USER" ) {
                    console.log(data.result.username);

        this.router.navigate(['user-page' ] , { queryParams: { username: data.result.username }});
      } else {
          console.log(data.result);
        this.invalidLogin = true;
        alert(data.message);
      }
    });
  }

  ngOnInit() {
    window.localStorage.removeItem('token');
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }

}