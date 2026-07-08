import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface CurrentUser {
 username: string;
 id?: number;
 email?: string;
 role?: string;
}

@Injectable({
 providedIn: 'root'
})
export class AuthService {

 private api = 'http://localhost:8080';
 private currentUserSubject = new BehaviorSubject<CurrentUser | null>(this.loadUserFromStorage());
 public currentUser$ = this.currentUserSubject.asObservable();

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
   ).pipe(
     tap(() => {
       const user: CurrentUser = { username: data.username };
       this.setCurrentUser(user);
     })
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

 logout(): Observable<any> {
   return this.http.post(
     `${this.api}/logout`,
     {},
     {
       responseType: 'text',
       withCredentials: true
     }
   ).pipe(
     tap(() => {
       this.clearCurrentUser();
     })
   );
 }

 getCurrentUser(): CurrentUser | null {
   return this.currentUserSubject.value;
 }

 getCurrentUserAsObservable(): Observable<CurrentUser | null> {
   return this.currentUser$;
 }

 isLoggedIn(): boolean {
   return this.currentUserSubject.value !== null;
 }

 private setCurrentUser(user: CurrentUser): void {
   localStorage.setItem('user', JSON.stringify(user));
   this.currentUserSubject.next(user);
 }

 private clearCurrentUser(): void {
   localStorage.removeItem('user');
   this.currentUserSubject.next(null);
 }

 private loadUserFromStorage(): CurrentUser | null {
   try {
     const userStr = localStorage.getItem('user');
     return userStr ? JSON.parse(userStr) : null;
   } catch (error) {
     console.error('Error loading user from storage:', error);
     return null;
   }
 }
}