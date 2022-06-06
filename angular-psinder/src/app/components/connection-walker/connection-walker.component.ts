import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Connection } from 'src/app/common/connection';
import { ConnectionService } from 'src/app/services/connection.service';

@Component({
  selector: 'app-connection-walker',
  templateUrl: './connection-walker.component.html',
  styleUrls: ['./connection-walker.component.css']
})
export class ConnectionWalkerComponent implements OnInit {

  connection: Connection = new Connection();

  constructor(private route: ActivatedRoute, private conService: ConnectionService, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleConnectionDetails();
    })
  }

  handleConnectionDetails() {
    const connId: string = this.route.snapshot.paramMap.get('id')!;
    this.conService.getConnectionById(connId).subscribe(
      data => {
        this.connection = data;
      }
    )
  }

  deleteConnection() {
    this.conService.deleteConnection(this.connection?.id.toString()!).subscribe({
      next: response => {
        alert("Połączenie zostało usunięte");
        this.router.navigateByUrl("/connections-walker");
      }, 
      error: err => {
        alert("Wystąpił błąd. Nie udało się usunąć połączenia");
      }
    })
  }

}
