import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Connection } from '../common/connection';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {

  private connUrl = environment.baseUrl + '/connections';

  constructor(private httpClient: HttpClient) { }

  getConnectionsForWalker(userId: string): Observable<Connection[]> {
    return this.httpClient.get<Connection[]>(`${this.connUrl}?walkerid=${userId}`);
  }

  getConnectionsForOwner(userId: string): Observable<Connection[]> {
    return this.httpClient.get<Connection[]>(`${this.connUrl}?ownerid=${userId}`);
  }
}
