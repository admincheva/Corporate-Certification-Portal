import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { CourseEnrollmentService, CoursesViewModel } from '../../core/services/course-enrollment.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html'
})
export class CoursesComponent implements OnInit {
  readonly vm$: Observable<CoursesViewModel>;

  constructor(private courseEnrollmentService: CourseEnrollmentService) {
    this.vm$ = this.courseEnrollmentService.vm$;
  }

  ngOnInit(): void {
    this.courseEnrollmentService.loadCourses().subscribe();
  }

  enroll(courseId: number): void {
    this.courseEnrollmentService.createEnrollment(courseId).subscribe();
  }

  clearMessage(): void {
    this.courseEnrollmentService.clearMessage();
  }
}