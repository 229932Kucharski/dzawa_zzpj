import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false
  
  @Input() error: string | undefined;

  constructor(private loginService: LoginService, 
    private router: Router) { }

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

}
