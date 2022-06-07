import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Chat } from 'src/app/common/chat';
import { ChatMessage } from 'src/app/common/chatMessage';
import { Connection } from 'src/app/common/connection';
import { ChatService } from 'src/app/services/chat.service';
import { ConnectionService } from 'src/app/services/connection.service';

@Component({
  selector: 'app-connection-owner',
  templateUrl: './connection-owner.component.html',
  styleUrls: ['./connection-owner.component.css']
})
export class ConnectionOwnerComponent implements OnInit {

  connection: Connection = new Connection();
  messages: ChatMessage[] = [];
  input: string = "";

  constructor(private route: ActivatedRoute, private conService: ConnectionService, 
    private router: Router, 
    private chatService: ChatService) { }

    ngOnInit(): void {
      this.route.paramMap.subscribe(() => {
        this.handleConnectionDetails();
      })
    }

    populateChat() {
      this.chatService.getMessagesForConnection(this.connection.id.toString()).subscribe(data => {
        this.messages = data.reverse();
      })
    }

    handleConnectionDetails() {
      const connId: string = this.route.snapshot.paramMap.get('id')!;
      this.conService.getConnectionById(connId).subscribe(
        data => {
          this.connection = data;
          this.populateChat();
        }
      )
    }

    deleteConnection() {
      this.conService.deleteConnection(this.connection?.id.toString()!).subscribe({
        next: response => {
          alert("Połączenie zostało usunięte");
          this.router.navigateByUrl("/connections-owner");
        }, 
        error: err => {
          alert("Wystąpił błąd. Nie udało się usunąć połączenia");
        }
      })
    }
  
    sendMessage() {
      if (this.input == "") {
        return;
      }
      let chat = new Chat();
      chat.conn_Id = this.connection.id;
      chat.user_Id = this.connection.owner.id;
      chat.text = this.input;
      this.chatService.addMessage(chat).subscribe({
        next: response => {
          this.input = "";
          this.ngOnInit();
        }, 
        error: err => {
          alert("Wystąpił problem, nie można wysłać wiadomości");
        }
      })
    }
  
    refreshChat() {
      this.ngOnInit();
    }

}
