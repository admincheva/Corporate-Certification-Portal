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
    private dashboardService: DashboardService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

    const currentUser = this.authService.getCurrentUser();

    if (currentUser?.username) {

      this.dashboardService
        .getDashboard(currentUser.username)
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