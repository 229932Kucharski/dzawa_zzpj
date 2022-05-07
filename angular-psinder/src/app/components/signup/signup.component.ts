import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpFormGroup!: FormGroup;
  error: string | undefined;
  authError: string | undefined;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.signUpFormGroup = this.formBuilder.group({
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
    })
  }

  signup() {
    if (this.signUpFormGroup.invalid) {
      this.error = "formError";
      return;
    }
    this.error = undefined;
    let user = new User();
    user.firstName = this.signUpFormGroup.value.firstName
    user.lastName = this.signUpFormGroup.value.lastName
    user.username = this.signUpFormGroup.value.username
    user.email = this.signUpFormGroup.value.email
    user.password = this.signUpFormGroup.value.password

    this.authService.register(user).subscribe({
      next: response => {
        const navigationExtras: NavigationExtras = {state: {data: 'Konto użytkownika zostało założone'}};
        this.router.navigateByUrl("/login", navigationExtras);
      },
      error: err => {
        if (err.error.includes("Username is already taken")) {
          this.authError = "Nazwa użytkownika jest już zajęta";
        } else if (err.error.includes("Email is already in use")) {
          this.authError = "Email jest już w użyciu";
        } else {
          this.authError = "Wystąpił nieznany błąd. Spróbuj jeszcze raz";
        }
      }
    })
  }

  clearError() {
    this.error = undefined;
    this.authError = undefined;
  }

  get username() {return this.signUpFormGroup.get('username')}
  get password() {return this.signUpFormGroup.get('password')}
  get firstName() {return this.signUpFormGroup.get('firstName')}
  get lastName() {return this.signUpFormGroup.get('lastName')}
  get email() {return this.signUpFormGroup.get('email')}

}