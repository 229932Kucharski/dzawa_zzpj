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
    this.conService.getConnectionsForWalker(userId!).pipe(map(cons => {
      return cons.filter(con => {
        if (con.pet.name.toLowerCase().includes(keyword)) {
          return true;
        } else if (con.pet.race.toLowerCase().includes(keyword)){
          return true;
        } else if (con.pet.address.city.toLowerCase().includes(keyword)){
          return true;
        } else if (con.pet.address.street.toLowerCase().includes(keyword)){
          return true;
        }else {
          return false;
        }
      })
    })).subscribe(res => this.connections = res);
  }

  handleListConnections() {
    let userId = sessionStorage.getItem("user_id");
    this.conService.getConnectionsForWalker(userId!).subscribe(res => this.connections = res);
  }

  connectionLink(con: Connection) {
    if (con.status == 'accepted') {
      return "/connections-walker";
    } else {
      return "/connections-walker"
    }
  }

  removeConnection() {
    console.log("USUWAM");
  }

  doSearch(value: string) {
    this.router.navigateByUrl(`/connections-walker/search/${value}`);
  }

}
