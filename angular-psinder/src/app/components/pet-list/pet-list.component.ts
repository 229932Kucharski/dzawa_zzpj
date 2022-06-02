import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';
import { Pet } from 'src/app/common/pet';
import { AuthService } from 'src/app/services/auth.service';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.css']
})
export class PetListComponent implements OnInit {

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

  constructor(private petService: PetService, 
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPets();
    })
  }

  listPets() {
    this.petService.getPets().subscribe(data => {
      this.pets = data
      if (this.pets.length > 0) {
        this.currentIndex = 0;
        this.currentPet = this.pets[this.currentIndex];
      }
    });
  }

  search() {
    console.log(this.race);
    console.log(this.smallSize);
    console.log(this.mediumSize);
    console.log(this.largeSize);
    console.log(this.city);
    console.log(this.street);
    console.log(this.distance);
  }

  add() {
    console.log("adding conn");
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
