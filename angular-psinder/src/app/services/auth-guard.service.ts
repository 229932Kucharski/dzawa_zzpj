import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(private router: Router, private loginService: LoginService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.loginService.isUserLoggedIn()) {
      return true;
    } else {
      this.router.navigateByUrl("/login");
      return false;
    }
  }
}
