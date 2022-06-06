import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';
import { Connection } from 'src/app/common/connection';
import { Pet } from 'src/app/common/pet';
import { User } from 'src/app/common/user';
import { UserData } from 'src/app/common/userData';
import { AuthService } from 'src/app/services/auth.service';
import { ConnectionService } from 'src/app/services/connection.service';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.css']
})
export class PetListComponent implements OnInit {

  petInConnections: Pet[] = [];

  pets: Pet[] = [];
  currentPet: Pet | undefined;
  currentIndex: number = 0;

  race: string = "";
  smallSize: boolean = false;
  mediumSize: boolean = false;
  largeSize: boolean = false;
  city: string = "";
  street: string = "";
  distance: number = 50;

  constructor(private petService: PetService, private conService: ConnectionService, 
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.listPets();
  }

  listPets() {
    const userId = sessionStorage.getItem('user_id')!;
    this.conService.getConnectionsForWalker(userId, "").subscribe(data => {
      data.forEach(conn => {
        this.petInConnections.push(conn.pet);
      });
      this.petService.getPets().subscribe(data => {
        data.forEach(pet => {
          if (!this.petExists(pet)) {
            this.pets.push(pet);
          }
        })
        if (this.pets.length > 0) {
          this.currentIndex = 0;
          this.currentPet = this.pets[this.currentIndex];
        }
      });
    }
    ) 
  }

  private petExists(pet: Pet) {
    return this.petInConnections.some(function(el) {
      return el.id === pet.id;
    }); 
  }

  readUserConnections() {
    
  }

  search() {
    let sizes = [];
    if (this.smallSize) {
      sizes.push("small"); 
    } 
    if (this.mediumSize) {
      sizes.push("medium")
    } 
    if (this.largeSize) {
      sizes.push("big")
    }
    let tempPets: Pet[] = [];
    this.petService.getPetsFiltered(this.race, sizes, this.city, this.street, this.distance).subscribe(data => {
      data.forEach(pet => {
        if (!this.petExists(pet)) {
          tempPets.push(pet);
        }
      })
      this.pets = tempPets;
      if (this.pets.length > 0) {
        this.currentIndex = 0;
        this.currentPet = this.pets[this.currentIndex];
      } else {
        this.pets = [];
      }
    });
  }

  add() {
    const userId = sessionStorage.getItem('user_id')!;
    if (this.currentPet == undefined) {
      return
    }
    let connection = new Connection();
    let owner = new UserData();
    let walker = new UserData();
    connection.walker = walker
    connection.pet = this.currentPet;
    connection.status = 'waiting'
    walker.id = Number(userId);
    this.petService.getPetOwner(this.currentPet.id).subscribe(data => {
      connection.owner = data
      this.conService.saveConnection(connection).subscribe({
        next: response => {
          alert("Dodano psa. Sprawdź swoje połączenia by sprawdzić jego status.");
          this.petInConnections.push(this.currentPet!);
          this.remove();
        }, 
        error: err => {
          alert("Wystąpił błąd. Nie udało się dodać połączenia");
        }
      })
    })
  }

  skip() {
    this.showNext();
  }

  remove() {
    this.pets.splice(this.currentIndex, 1);
    if (this.currentIndex >= this.pets.length) {
      this.currentIndex--;
    }
    this.currentPet = this.pets[this.currentIndex]
  }

  showNext() {
    this.currentIndex++;
    if (this.currentIndex > this.pets.length - 1) {
      this.currentIndex = 0;
    }
    this.currentPet = this.pets[this.currentIndex]
  }

}
