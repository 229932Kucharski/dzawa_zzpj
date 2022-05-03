import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  authenticated = false;
  private userUrl = environment.baseUrl + '/user';

  constructor(private httpClient: HttpClient) { }

  authenticate(credentials: any, callback: any) {
    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});
    this.httpClient.get(this.userUrl, {headers: headers}).subscribe(response => {
      if (response.hasOwnProperty('name')) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
      return callback && callback();
    })
  }
}
