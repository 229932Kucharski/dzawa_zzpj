import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-psinder';

  constructor(private loginService: LoginService, private httpClient: HttpClient, private router: Router) {
    // this.loginService.authenticate(undefined, undefined);
  }

  logout() {
    this.httpClient.post('logout', {}).pipe(
      finalize(() => {
        this.loginService.authenticated = false;
      this.router.navigateByUrl('/login');
      })
    ).subscribe();
  }
}
