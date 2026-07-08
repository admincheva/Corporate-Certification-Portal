import { Component, OnInit, PLATFORM_ID, Inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import {
  EnrollmentService,
  EnrollmentSummary
} from '../../services/enrollment.service';

@Component({
  selector: 'app-enrollments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './enrollments.html',
  styleUrls: ['./enrollments.css']
})
export class EnrollmentsComponent implements OnInit {

  enrollments: EnrollmentSummary[] = [];
  currentUsername: string = '';

  constructor(
    private service: EnrollmentService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };

    if (!user.username) {
      this.enrollments = [];
      return;
    }

    this.currentUsername = user.username;

    this.service
      .getCurrentUserEnrollments(user.username)
      .subscribe({
        next: (data: EnrollmentSummary[]) => {
          this.enrollments = data;
        },
        error: (err) => {
          console.error('Error loading enrollments:', err);
        }
      });
  }

  completeEnrollment(enrollmentId: number): void {
    this.service.completeEnrollment(enrollmentId).subscribe({
      next: () => {
        console.log('Enrollment completed successfully');
        this.loadEnrollments();
      },
      error: (err) => {
        console.error('Error completing enrollment:', err);
      }
    });
  }

  cancelEnrollment(enrollmentId: number): void {
    this.service.cancelEnrollment(enrollmentId).subscribe({
      next: () => {
        console.log('Enrollment cancelled successfully');
        this.loadEnrollments();
      },
      error: (err) => {
        console.error('Error cancelling enrollment:', err);
      }
    });
  }
}
