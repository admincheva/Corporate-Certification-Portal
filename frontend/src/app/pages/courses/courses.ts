import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';
import {
  CreateEnrollmentRequest,
  EnrollmentService
} from '../../services/enrollment.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './courses.html',
  styleUrls: ['./courses.css']
})
export class CoursesComponent implements OnInit {

  allCourses: Course[] = [];
  courses: Course[] = [];
  enrollingCourseIds = new Set<number>();
  
  filterCategory: string = '';
  filterProvider: string = '';
  maxPrice: number | null = null;

  categories: string[] = [];
  providers: string[] = [];

  constructor(
    private service: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses() {
    this.service.getAll().subscribe(data => {
      this.allCourses = data;
      this.extractFilters();
      this.applyFilters();
    });
  }

  extractFilters(): void {
    const uniqueCategories = new Set(
      this.allCourses
        .map(c => c.category)
        .filter((c): c is string => !!c)
    );
    this.categories = Array.from(uniqueCategories).sort();

    const uniqueProviders = new Set(
      this.allCourses.map(c => c.provider)
    );
    this.providers = Array.from(uniqueProviders).sort();
  }

  applyFilters(): void {
    this.courses = this.allCourses.filter(course => {
      const matchesCategory = 
        !this.filterCategory || 
        course.category === this.filterCategory;
      
      const matchesProvider = 
        !this.filterProvider || 
        course.provider === this.filterProvider;
      
      const matchesPrice = 
        this.maxPrice === null || 
        (course.price !== undefined && 
         course.price <= this.maxPrice);

      return matchesCategory && matchesProvider && matchesPrice;
    });
  }

  resetFilters(): void {
    this.filterCategory = '';
    this.filterProvider = '';
    this.maxPrice = null;
    this.applyFilters();
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