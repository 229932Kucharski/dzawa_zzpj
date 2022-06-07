import { Component, OnInit } from '@angular/core';
import { Pet } from 'src/app/common/pet';
import { UserData } from 'src/app/common/userData';
import { AuthService } from 'src/app/services/auth.service';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  choosenPet: Pet | undefined;
  currentUser: UserData | undefined;
  pets: Pet[] = [];

  constructor(private authService: AuthService, private petService: PetService) { }

  ngOnInit(): void {
    this.getUserData();
    this.getPetsForUser();
  }

  getPetsForUser() {
    const userId = sessionStorage.getItem('user_id')!;
    this.petService.getPetsForUser(userId).subscribe(
      data => {
        this.pets = data;
      }
    )
  }

  getUserData() {
    const username = sessionStorage.getItem('username')!;
    this.authService.getUser(username).subscribe(
      data => {
        this.currentUser = data;
      }
    )
  }

  choosePet(pet: Pet) {
    this.choosenPet = pet;
  }

  deletePet() {
    this.petService.deletePet(this.choosenPet!.id).subscribe({
      next: response => {
        alert("Pies został usunięty");
        this.ngOnInit();
      }, 
      error: err => {
        alert("Wystąpił błąd. Nie udało się usunąć psa");
      }
    })
  }

  

}
