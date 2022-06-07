import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PetWithUser } from 'src/app/common/petWithUser';
import { UserData } from 'src/app/common/userData';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-pet-add',
  templateUrl: './pet-add.component.html',
  styleUrls: ['./pet-add.component.css']
})
export class PetAddComponent implements OnInit {

  petFormGroup!: FormGroup;
  url: string | ArrayBuffer | null = "assets/img/dogs/undefined.jpg";
  file: File | undefined;
  error: string | undefined;

  constructor(private router: Router, private formBuilder: FormBuilder, private petService: PetService) { }

  ngOnInit(): void {
    this.petFormGroup = this.formBuilder.group({
      name: new FormControl('', [Validators.required, Validators.minLength(1)]),
      race: new FormControl('', [Validators.required, Validators.minLength(2)]),
      size: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required, Validators.minLength(3)]),
      street: new FormControl('',),
    })
  }

  clearError() {
    this.error = undefined;
  }

  addPet() {
    if (this.petFormGroup.invalid) {
      this.error = "formError";
      return;
    }
    let pet = new PetWithUser();
    let user = new UserData();
    user.id = Number(sessionStorage.getItem("user_id"));
    pet.user = user;
    pet.name = this.petFormGroup.value.name
    pet.race = this.petFormGroup.value.race
    pet.size = this.petFormGroup.value.size
    pet.description = this.petFormGroup.value.description
    pet.address = {city : this.petFormGroup.value.city, street : this.petFormGroup.value.street}
    this.petService.savePet(pet).subscribe({
      next: response => {
        alert("Pies został dodany pomyślnie");
        this.router.navigateByUrl("/profile");
      }, 
      error: err => {
        alert("Wystąpił błąd. Nie udało się dodać psa");
      }
    })
  }

  handleFileInput(event: any) {
    if (event.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      this.file = event.target.files[0];
      reader.onload = (event) => {
        this.url = event.target!.result;
      }
    }
  }

  get name() {return this.petFormGroup.get('name')}
  get race() {return this.petFormGroup.get('race')}
  get size() {return this.petFormGroup.get('size')}
  get city() {return this.petFormGroup.get('city')}
  get street() {return this.petFormGroup.get('street')}
  get description() {return this.petFormGroup.get('description')}
}
