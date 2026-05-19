import { Component } from '@angular/core';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {

    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  login() {

    this.authService.login(this.form.value)
      .subscribe({

        next: (response) => {

          console.log(response);

          alert('Login successful');

        },

        error: (error) => {

          console.error(error);

          alert('Invalid username or password');

        }

      });
  }
}