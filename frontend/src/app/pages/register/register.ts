import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

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
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {

    this.form = this.fb.group({

      username: [
        '',
        Validators.required
      ],

      password: [
        '',
        Validators.required
      ],

      confirmPassword: [
        '',
        Validators.required
      ]

    });

  }

  register(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();
      return;
    }

    const password =
      this.form.get('password')?.value;

    const confirmPassword =
      this.form.get('confirmPassword')?.value;

    if (password !== confirmPassword) {

      alert('Passwords do not match');
      return;
    }

    this.authService.register({

      username:
        this.form.get('username')?.value,

      password

    }).subscribe({

      next: () => {

        alert(
          'Registration successful'
        );

        this.router.navigate(['/']);

      },

      error: (error: any) => {

        console.error(error);

        alert('Registration failed');

      }

    });
  }
}