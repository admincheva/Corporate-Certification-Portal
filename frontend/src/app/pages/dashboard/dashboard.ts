import {
  Component,
  OnInit
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

import {
  DashboardService
} from '../../services/dashboard.service';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent
implements OnInit {

  dashboard: any;

  constructor(
    private dashboardService:
    DashboardService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

   if (typeof window !== 'undefined') {

  const user = JSON.parse(
    localStorage.getItem('user') || '{}'
  );

  this.dashboardService
    .getDashboard(user.username)
    .subscribe({

      next: (response: any) => {

        this.dashboard = response;

      },

      error: (error: any) => {

        console.error(error);

      }

    });
  }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error: any) => {
        console.error(error);
        this.router.navigate(['/']);
      }
    });
  }
}