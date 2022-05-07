import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, map, Observable } from 'rxjs';
import { EMPTY_SUBSCRIPTION } from 'rxjs/internal/Subscription';
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
