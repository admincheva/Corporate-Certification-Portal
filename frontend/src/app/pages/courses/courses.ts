import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CourseService } from '../../services/course';
import { Course } from '../../models/course.model';
import {
  CreateEnrollmentRequest,
  EnrollmentService,
  EnrollmentSummary
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
  currentUsername: string | null = null;
  userEnrollments: Map<number, boolean> = new Map();

  filterTitle: string = '';
  filterCategory: string = '';
  filterProvider: string = '';
  filterRefundable: boolean | undefined = undefined;
  filterMinPrice: number | null = null;
  maxPrice: number | null = null;

  categories: string[] = [];
  providers: string[] = [];

  constructor(
    private service: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  private loadCurrentUser(): void {
    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };
    this.currentUsername = user.username || null;

    if (this.currentUsername) {
      this.loadUserEnrollments();
    } else {
      this.loadCourses();
    }
  }

  private loadUserEnrollments(): void {
    if (!this.currentUsername) return;
    this.enrollmentService
      .getCurrentUserEnrollments(this.currentUsername)
      .subscribe({
        next: (enrollments: EnrollmentSummary[]) => {
          enrollments.forEach(enrollment => {
            this.userEnrollments.set(enrollment.courseId, true);
          });
          this.loadCourses();
        },
        error: () => {
          this.loadCourses();
        }
      });
  }

  loadCourses(): void {
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
      const matchesTitle =
        !this.filterTitle ||
        course.title.toLowerCase().includes(this.filterTitle.toLowerCase());

      const matchesCategory =
        !this.filterCategory ||
        course.category === this.filterCategory;

      const matchesProvider =
        !this.filterProvider ||
        course.provider === this.filterProvider;

      const matchesRefundable =
        this.filterRefundable === undefined ||
        course.refundable === this.filterRefundable;

      const matchesMinPrice =
        this.filterMinPrice === null ||
        (course.price !== undefined && course.price >= this.filterMinPrice);

      const matchesMaxPrice =
        this.maxPrice === null ||
        (course.price !== undefined && course.price <= this.maxPrice);

      return matchesTitle && matchesCategory && matchesProvider &&
             matchesRefundable && matchesMinPrice && matchesMaxPrice;
    });
  }

  resetFilters(): void {
    this.filterTitle = '';
    this.filterCategory = '';
    this.filterProvider = '';
    this.filterRefundable = undefined;
    this.filterMinPrice = null;
    this.maxPrice = null;
    this.applyFilters();
  }

  isUserEnrolled(courseId: number | undefined): boolean {
    if (!courseId) return false;
    return this.userEnrollments.has(courseId);
  }

  enroll(course: Course): void {
    const courseId = course.id;

    if (!courseId) {
      alert('Course id is missing.');
      return;
    }

    if (!this.currentUsername) {
      alert('Please log in again.');
      return;
    }

    const enrollmentRequest: CreateEnrollmentRequest = {
      username: this.currentUsername,
      courseId
    };

    this.enrollingCourseIds.add(courseId);

    this.enrollmentService
      .createEnrollment(enrollmentRequest)
      .subscribe({
        next: () => {
          this.userEnrollments.set(courseId, true);
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