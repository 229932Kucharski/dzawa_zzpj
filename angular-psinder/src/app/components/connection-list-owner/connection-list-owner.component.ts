import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Connection } from 'src/app/common/connection';
import { ConnectionService } from 'src/app/services/connection.service';

@Component({
  selector: 'app-connection-list-owner',
  templateUrl: './connection-list-owner.component.html',
  styleUrls: ['./connection-list-owner.component.css']
})
export class ConnectionListOwnerComponent implements OnInit {

  connections: Connection[] = [];
  choosenConnection: Connection | undefined;

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
    this.conService.getConnectionsForOwner(userId!, keyword).subscribe(res => this.connections = res);
  }

  handleListConnections() {
    let userId = sessionStorage.getItem("user_id");
    this.conService.getConnectionsForOwner(userId!, "").subscribe(res => this.connections = res);
  }

  buttonEvent(conn: Connection) {
    this.choosenConnection = conn;
  }

  acceptConnection() {
    this.conService.acceptConnection(this.choosenConnection?.id.toString()!).subscribe({
      next: response => {
        alert("Połączenie zostało zaakceptowane");
        this.ngOnInit();
      }, 
      error: err => {
        alert("Wystąpił błąd. Nie udało się zaakcpetować połączenia");
      }
    })
  }

  removeConnection() {
    this.conService.deleteConnection(this.choosenConnection?.id.toString()!).subscribe({
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
    this.router.navigateByUrl(`/connections-owner/search/${value}`);
  }
}
