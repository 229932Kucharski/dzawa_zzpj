import { Component, OnInit } from '@angular/core';
import { UserData } from 'src/app/common/userData';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  currentUser: UserData | undefined;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.getUserData();
  }

  getUserData() {
    const username = sessionStorage.getItem('username')!;
    this.authService.getUser(username).subscribe(
      data => {
        this.currentUser = data;
      }
    )
  }

  

}
