import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pet } from 'src/app/common/pet';
import { LoginService } from 'src/app/services/login.service';
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
    private loginService: LoginService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPets();
    })
  }

  listPets() {
    this.petService.getPets().subscribe(data => this.pets = data);
  }

  authenticated() {
    return this.loginService.authenticated;
  }

}
