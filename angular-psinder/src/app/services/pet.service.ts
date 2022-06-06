import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pet } from '../common/pet';
import { UserData } from '../common/userData';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private petsUrl = environment.baseUrl + '/pets';

  constructor(private httpClient: HttpClient) { }

  getPets(): Observable<Pet[]> {
    return this.httpClient.get<Pet[]>(this.petsUrl);
  }

  getPetOwner(petId: string): Observable<UserData> {
    return this.httpClient.get<UserData>(`${this.petsUrl}/${petId}/owner`);
  }

  getPetsFiltered(race: string, sizes: string[], city: string, street: string, distance: number): Observable<Pet[]> {
    let filtredPetsUrl = this.petsUrl + "/filtered?";
    if (race != "") {
      filtredPetsUrl += `race=${race}&`;
    }
    if (sizes.length != 0) {
      for (var size of sizes) {
        filtredPetsUrl += `size=${size}&`;
      }
    }
    if (city != "") {
      filtredPetsUrl += `city=${city}&`;
    }
    if (street != "") {
      filtredPetsUrl += `street=${street}&`;
    }
    filtredPetsUrl += `distance=${distance}&`;
    
    return this.httpClient.get<Pet[]>(filtredPetsUrl);
  }
}