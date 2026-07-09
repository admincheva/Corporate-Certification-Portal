import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Submission } from '../models/submission.model';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {

  private api = 'http://localhost:8080/submissions';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Submission[]> {
    return this.http.get<Submission[]>(
      this.api,
      { withCredentials: true }
    );
  }

  getMySubmissions(username: string): Observable<Submission[]> {
    return this.http.get<Submission[]>(
      `${this.api}/${encodeURIComponent(username)}`,
      { withCredentials: true }
    );
  }

  create(submission: Submission): Observable<Submission> {
    return this.http.post<Submission>(
      this.api,
      submission,
      { withCredentials: true }
    );
  }

  createWithFile(formData: FormData): Observable<Submission> {
    return this.http.post<Submission>(
      `${this.api}/upload`,
      formData,
      { withCredentials: true }
    );
  }

  getById(id: number): Observable<Submission> {
    return this.http.get<Submission>(
      `${this.api}/id/${id}`,
      { withCredentials: true }
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.api}/${id}`,
      { withCredentials: true }
    );
  }

  approveSubmission(id: number): Observable<void> {
    return this.http.put<void>(
      `${this.api}/${id}/approve`,
      {},
      { withCredentials: true }
    );
  }

  rejectSubmission(id: number): Observable<void> {
    return this.http.put<void>(
      `${this.api}/${id}/reject`,
      {},
      { withCredentials: true }
    );
  }

}