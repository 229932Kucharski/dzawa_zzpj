import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  constructor(private petService: PetService, 
    private route: ActivatedRoute,
    private loginService: AuthService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPets();
    })
  }

  listPets() {
    this.petService.getPets().subscribe(data => this.pets = data);
  }

  authenticated() {
    return this.loginService.isUserLoggedIn();
  }

}
