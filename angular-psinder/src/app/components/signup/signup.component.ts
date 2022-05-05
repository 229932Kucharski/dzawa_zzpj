import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpFormGroup!: FormGroup;
  error: string | undefined;

  constructor(private formBuilder: FormBuilder) { }

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
    }else {
      this.error = undefined;
    }
  }

  clearError() {
    this.error = undefined;
  }

  get username() {return this.signUpFormGroup.get('username')}
  get password() {return this.signUpFormGroup.get('password')}
  get firstName() {return this.signUpFormGroup.get('firstName')}
  get lastName() {return this.signUpFormGroup.get('lastName')}
  get email() {return this.signUpFormGroup.get('email')}

}