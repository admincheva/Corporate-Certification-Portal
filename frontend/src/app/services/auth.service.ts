import { Injectable } from '@angular/core';
import {
  HttpClient
} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api =
    'http://localhost:8080/auth';

  constructor(
    private http: HttpClient
  ) {}

 login(data: any) {

  return this.http.post(
    `${this.api}/login`,
    data,
    {
      responseType: 'text'
    }
  );
}

  register(data: any) {

  return this.http.post(
    `${this.api}/register`,
    data,
    {
      responseType: 'text'
    }
  );
}
}