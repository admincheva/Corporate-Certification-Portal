import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  EnrollmentService,
  EnrollmentSummary
} from '../../services/enrollment.service';

@Component({
  selector: 'app-enrollments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './enrollments.html'
})
export class EnrollmentsComponent implements OnInit {

  enrollments: EnrollmentSummary[] = [];

  constructor(private service: EnrollmentService) {}

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments() {
    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };

    if (!user.username) {
      this.enrollments = [];
      return;
    }

    this.service.getCurrentUserEnrollments(user.username).subscribe(data => {
      this.enrollments = data;
    });
  }
}
