import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

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
  imports: [ReactiveFormsModule, RouterLink],
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

          const username =
            this.form.get('username')?.value as string;

          localStorage.setItem(
            'user',
            JSON.stringify({
              username
            })
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