import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../common/user';
import { UserData } from '../common/userData';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authenticateUrl = environment.baseUrl + '/authenticate';
  private registerUrl = environment.baseUrl + '/register';
  private userUrl = environment.baseUrl + '/users';

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

  getUser(username: string) {
    return this.httpClient.get<UserData>(`${this.userUrl}?username=${username}`);
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
    sessionStorage.removeItem("token");
    this.username.next("");
  }

}
