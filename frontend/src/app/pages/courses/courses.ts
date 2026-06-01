import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html'
})
export class CoursesComponent implements OnInit {

  courses: Course[] = [];

  constructor(private service: CourseService) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses() {
    this.service.getAll().subscribe(data => {
      this.courses = data;
    });
  }
}