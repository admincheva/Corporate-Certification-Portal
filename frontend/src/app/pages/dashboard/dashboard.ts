import {
  Component,
  OnInit
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import {
  DashboardService
} from '../../services/dashboard.service';

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
    DashboardService
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
  }}