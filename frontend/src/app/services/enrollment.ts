import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Submission } from '../models/submission.model';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {

  private apiUrl = 'http://localhost:8080/api/submissions';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Submission[]> {
    return this.http.get<Submission[]>(this.apiUrl);
  }

  create(submission: Submission): Observable<Submission> {
    return this.http.post<Submission>(this.apiUrl, submission);
  }
}
