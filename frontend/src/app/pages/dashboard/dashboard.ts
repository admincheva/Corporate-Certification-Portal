import { Component, OnInit, PLATFORM_ID, Inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DashboardService } from '../../services/dashboard.service';
import { AuthService } from '../../services/auth.service';
import { EnrollmentService, EnrollmentSummary } from '../../services/enrollment.service';
import { CourseService } from '../../services/course.service';
import { SubmissionService } from '../../services/submission.service';
import { Course } from '../../models/course.model';
import { Submission } from '../../models/submission.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  dashboard: any;
  enrollments: EnrollmentSummary[] = [];
  allCourses: Course[] = [];
  submissions: Submission[] = [];
  username = '';
  currentDate = new Date();

  get totalCourses(): number {
    return this.allCourses.length;
  }

  get totalEnrollments(): number {
    return this.enrollments.length;
  }

  get completedCourses(): number {
    return this.enrollments.filter(e => e.status === 'COMPLETED').length;
  }

  get certificates(): number {
    return this.submissions.filter(s => s.status === 'APPROVED').length;
  }

  get progressPercentage(): number {
    if (this.totalEnrollments === 0) return 0;
    return Math.round((this.completedCourses / this.totalEnrollments) * 100);
  }

  get recentEnrollments(): EnrollmentSummary[] {
    return this.enrollments.slice(0, 5);
  }

  get recommendedCourses(): Course[] {
    const enrolledIds = new Set(this.enrollments.map(e => e.courseId));
    return this.allCourses
      .filter(c => c.id !== undefined && !enrolledIds.has(c.id))
      .slice(0, 3);
  }

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private enrollmentService: EnrollmentService,
    private courseService: CourseService,
    private submissionService: SubmissionService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) return;

    const currentUser = this.authService.getCurrentUser();
    if (!currentUser?.username) return;

    this.username = currentUser.username;

    this.dashboardService.getDashboard(this.username).subscribe({
      next: (res: any) => { this.dashboard = res; },
      error: (err) => { console.error('Dashboard error:', err); }
    });

    this.enrollmentService.getCurrentUserEnrollments(this.username).subscribe({
      next: (data) => { this.enrollments = data; },
      error: (err) => { console.error('Enrollments error:', err); }
    });

    this.courseService.getAll().subscribe({
      next: (courses) => { this.allCourses = courses; },
      error: (err) => { console.error('Courses error:', err); }
    });

    this.submissionService.getMySubmissions(this.username).subscribe({
      next: (subs) => { this.submissions = subs; },
      error: (err) => { console.error('Submissions error:', err); }
    });
  }

  getGreeting(): string {
    const hour = new Date().getHours();
    if (hour < 12) return 'Good morning';
    if (hour < 17) return 'Good afternoon';
    return 'Good evening';
  }

  getStatusClass(status: string): string {
    switch (status?.toUpperCase()) {
      case 'COMPLETED': return 'status-completed';
      case 'CANCELLED': return 'status-cancelled';
      default: return 'status-enrolled';
    }
  }
}