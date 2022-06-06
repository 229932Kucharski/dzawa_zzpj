import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { Connection } from 'src/app/common/connection';
import { ConnectionService } from 'src/app/services/connection.service';

@Component({
  selector: 'app-connection-list-walker',
  templateUrl: './connection-list-walker.component.html',
  styleUrls: ['./connection-list-walker.component.css']
})
export class ConnectionListWalkerComponent implements OnInit {

  connections: Connection[] = [];
  connectionToDelete: Connection | undefined;

  searchMode!: boolean;

  constructor(private conService: ConnectionService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listConnections();
    });
  }

  listConnections() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.handleSearchConnections();
    } else {
      this.handleListConnections();
    }
  }

  handleSearchConnections() {
    const keyword: string = this.route.snapshot.paramMap.get('keyword')!.toLowerCase();
    let userId = sessionStorage.getItem("user_id");
    this.conService.getConnectionsForWalker(userId!, keyword).subscribe(res => this.connections = res);
  }

  handleListConnections() {
    let userId = sessionStorage.getItem("user_id");
    this.conService.getConnectionsForWalker(userId!, "").subscribe(res => this.connections = res);
  }

  connectionLink(con: Connection) {
    let conId = con.id.toString();
    if (con.status == 'accepted') {
      return "/connection-walker/" + conId;
    } else {
      return "/connections-walker"
    }
  }

  deleteEvent(conn: Connection) {
    this.connectionToDelete = conn;
  }

  removeConnection() {
    this.conService.deleteConnection(this.connectionToDelete?.id.toString()!).subscribe({
      next: response => {
        alert("Połączenie zostało usunięte");
        this.ngOnInit();
      }, 
      error: err => {
        alert("Wystąpił błąd. Nie udało się usunąć połączenia");
      }
    })
  }

  doSearch(value: string) {
    this.router.navigateByUrl(`/connections-walker/search/${value}`);
  }

}
