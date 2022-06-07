import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Chat } from '../common/chat';
import { ChatMessage } from '../common/chatMessage';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private chatUrl = environment.baseUrl + '/chat';

  constructor(private httpClient: HttpClient) { }

  getMessagesForConnection(connId: string): Observable<ChatMessage[]> {
    return this.httpClient.get<ChatMessage[]>(`${this.chatUrl}?id=${connId}`);
  }

  addMessage(chat: Chat) {
    return this.httpClient.post<Chat>(`${this.chatUrl}/create`, chat);
  }
}
