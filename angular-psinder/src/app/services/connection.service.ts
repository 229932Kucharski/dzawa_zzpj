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

  getConnectionsForWalker(userId: string, keyword: string): Observable<Connection[]> {
    if (keyword == "") {
      return this.httpClient.get<Connection[]>(`${this.connUrl}?walkerid=${userId}`);
    } else {
      return this.httpClient.get<Connection[]>(`${this.connUrl}?walkerid=${userId}&keyword=${keyword}`);
    }
    
  }

  acceptConnection(connId: string) {
    return this.httpClient.put(`${this.connUrl}/status-update?connectionid=${connId}&status=accepted`, null);
  }

  getConnectionsForOwner(userId: string, keyword: string): Observable<Connection[]> {
    if (keyword == "") {
      return this.httpClient.get<Connection[]>(`${this.connUrl}?ownerid=${userId}`);
    } else {
      return this.httpClient.get<Connection[]>(`${this.connUrl}?ownerid=${userId}&keyword=${keyword}`);
    }
  }

  getConnectionById(connId: string): Observable<Connection> {
    return this.httpClient.get<Connection>(`${this.connUrl}?id=${connId}`);
  }

  saveConnection(connection: Connection): Observable<any> {
    return this.httpClient.post<Connection>(`${this.connUrl}/create`, connection);
  }

  deleteConnection(connectionId: string): Observable<any> {
    return this.httpClient.delete(`${this.connUrl}/delete?id=${connectionId}`, {responseType: 'text'});
  }
}
