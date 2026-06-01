import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollmentService } from '../../services/enrollment.service';
import { Enrollment } from '../../models/enrollment.model';

@Component({
  selector: 'app-enrollments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './enrollments.html'
})
export class EnrollmentsComponent implements OnInit {

  enrollments: Enrollment[] = [];

  constructor(private service: EnrollmentService) {}

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments() {
    this.service.getAll().subscribe(data => {
      this.enrollments = data;
    });
  }
}
