import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Enrollment } from '../models/enrollment.model';

export interface EnrollmentSummary {
  id: number;
  username: string;
  courseId: number;
  courseTitle: string;
  provider: string;
  enrolledAt: string;
  status: string;
}

export interface CreateEnrollmentRequest {
  username: string;
  courseId: number;
  status?: string;
}

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {

  private apiUrl = 'http://localhost:8080/enrollments';

  constructor(private http: HttpClient) {}

  getCurrentUserEnrollments(
    username: string
  ): Observable<EnrollmentSummary[]> {
    return this.http.get<EnrollmentSummary[]>(
      `${this.apiUrl}/${encodeURIComponent(username)}`,
      { withCredentials: true }
    );
  }

  createEnrollment(
    enrollment: CreateEnrollmentRequest
  ): Observable<EnrollmentSummary> {
    return this.http.post<EnrollmentSummary>(
      this.apiUrl,
      enrollment,
      { withCredentials: true }
    );
  }

  getAll(): Observable<Enrollment[]> {

    return this.http.get<Enrollment[]>(
      this.apiUrl,
      { withCredentials: true }
    );
  }

  create(enrollment: Enrollment): Observable<Enrollment> {

    return this.http.post<Enrollment>(
      this.apiUrl,
      enrollment,
      { withCredentials: true }
    );
  }
}
