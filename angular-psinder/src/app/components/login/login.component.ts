import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false
  
  error: string | undefined;
  message: string | undefined;

  constructor(private loginService: AuthService, 
    private router: Router) {
      const navigation = this.router.getCurrentNavigation();
      const state = navigation?.extras.state as {data: string};
      if (state !== undefined) {
        this.message = state.data;
      }
    }

  ngOnInit(): void {
  }

  checkLogin() {
    (this.loginService.authenticate(this.username, this.password).subscribe(
      data => {
        this.router.navigateByUrl("/")
        this.invalidLogin = false
      },
      error => {
        this.invalidLogin = true
        this.error = error.message;
      }
    )
    );
  }

  clearError() {
    this.error = undefined;
  }

}
