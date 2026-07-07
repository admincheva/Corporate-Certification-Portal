import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';
import {
  CreateEnrollmentRequest,
  EnrollmentService
} from '../../services/enrollment.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html'
})
export class CoursesComponent implements OnInit {

  courses: Course[] = [];
  enrollingCourseIds = new Set<number>();

  constructor(
    private service: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses() {
    this.service.getAll().subscribe(data => {
      this.courses = data;
    });
  }

  enroll(course: Course): void {
    const courseId = course.id;

    if (!courseId) {
      alert('Course id is missing.');
      return;
    }

    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };

    if (!user.username) {
      alert('Please log in again.');
      return;
    }

    const enrollmentRequest: CreateEnrollmentRequest = {
      username: user.username,
      courseId
    };

    this.enrollingCourseIds.add(courseId);

    this.enrollmentService
      .createEnrollment(enrollmentRequest)
      .subscribe({
        next: () => {
          alert(`Enrolled in "${course.title}"`);
          this.enrollingCourseIds.delete(courseId);
        },
        error: (error: unknown) => {
          console.error(error);
          alert('Enrollment failed.');
          this.enrollingCourseIds.delete(courseId);
        }
      });
  }
}