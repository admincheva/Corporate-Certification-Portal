import { Router } from '@angular/router';
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
  styleUrls: ['./login.css']
})
export class LoginComponent {

  form!: FormGroup;

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
    ]

  });

} 



login() {

  if (this.form.invalid) {

    this.form.markAllAsTouched();
    return;
  }

  this.authService
      .login(this.form.value)
      .subscribe({

        next: (response) => {

          console.log(response);

          localStorage.setItem(
            'user',
            JSON.stringify(response)
          );

          this.router.navigate([
            '/dashboard'
          ]);

        },

        error: (error) => {

          console.error(error);

          alert(
            'Invalid username or password'
          );

        }

      });
}
}