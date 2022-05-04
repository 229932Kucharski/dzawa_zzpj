import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-psinder';

  constructor(private loginService: LoginService) {
    
  }

  authenticated() {
    return this.loginService.isUserLoggedIn();
  }
}
