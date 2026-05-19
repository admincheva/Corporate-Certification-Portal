import { Component } from '@angular/core';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {

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

  register() {

    this.authService.register(this.form.value)
      .subscribe({

        next: (response) => {

          console.log(response);

          alert('User registered successfully');

        },

        error: (error) => {

          console.error(error);

          alert('Register failed');

        }

      });
  }
}