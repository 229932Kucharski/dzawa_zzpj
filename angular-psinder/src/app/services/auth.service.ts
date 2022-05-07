import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authenticateUrl = environment.baseUrl + '/authenticate';
  private registerUrl = environment.baseUrl + '/register';

  username: Subject<string> = new BehaviorSubject<string>("");

  constructor(private httpClient: HttpClient) { 
    this.username.next(sessionStorage.getItem("username")!);
  }

  authenticate(username: string, password: string) {
    return this.httpClient.post<any>(this.authenticateUrl, { username, password })
    .pipe(
      map(userData => {
        sessionStorage.setItem("username", username);
        this.username.next(username);
        let tokenStr = "Bearer " + userData.token;
        sessionStorage.setItem("token", tokenStr);
        return userData;
      })
    );
  }

  register(user: User) {
    return this.httpClient.post<User>(this.registerUrl, user)
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("username");
    // this.username.next("");
  }

}
