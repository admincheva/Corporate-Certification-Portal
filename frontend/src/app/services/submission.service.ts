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

}