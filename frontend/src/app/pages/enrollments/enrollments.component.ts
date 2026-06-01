import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EnrollmentService } from '../../services/enrollment.service';
import { Enrollment } from '../../models/enrollment.model';

@Component({
  selector: 'app-enrollments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './enrollments.html',
  styleUrls: ['./enrollments.css']
})
export class EnrollmentsComponent implements OnInit {

  enrollments: Enrollment[] = [];

  constructor(private service: EnrollmentService) {}

  ngOnInit(): void {

    this.loadEnrollments();
  }

  loadEnrollments(): void {

    this.service.getAll().subscribe({

      next: (data: Enrollment[]) => {

        this.enrollments = data;
      },

      error: (err) => {

        console.error(err);
      }
    });
  }
}
