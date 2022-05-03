import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pet } from '../common/pet';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private petsUrl = environment.baseUrl + '/pets';

  constructor(private httpClient: HttpClient) { }

  getPets(): Observable<Pet[]> {
    return this.httpClient.get<Pet[]>(this.petsUrl);
  }
}
