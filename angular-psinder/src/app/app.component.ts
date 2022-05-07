import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  username: string = "";

  constructor(private loginService: AuthService) { }

  ngOnInit(): void {
    this.getUsername();
  }

  getUsername() {
    this.loginService.username.subscribe(
      data => this.username = data
    )
  }

  isAuthenticated() {
    return this.loginService.isUserLoggedIn();
  }

}
