import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api =
    'http://localhost:8080';

  constructor(
    private http: HttpClient
  ) {}

 login(data: {
   username: string;
   password: string;
 }) {
  const formBody = new URLSearchParams();
  formBody.set('username', data.username);
  formBody.set('password', data.password);

  return this.http.post(
    `${this.api}/login`,
    formBody.toString(),
    {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      }),
      responseType: 'text',
      withCredentials: true
    }
  );
}

  register(data: any) {

  return this.http.post(
    `${this.api}/auth/register`,
    data,
    {
      responseType: 'text'
    }
  );
}

  logout() {
    localStorage.removeItem('user');
    return this.http.post(
      `${this.api}/logout`,
      {},
      {
        withCredentials: true
      }
    );
  }
}