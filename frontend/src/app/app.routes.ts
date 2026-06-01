import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { CoursesComponent } from './pages/courses/courses';
import { EnrollmentsComponent } from './pages/enrollments/enrollments';
import { SubmissionsComponent } from './pages/submissions/submissions';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'courses', component: CoursesComponent },
  { path: 'enrollments', component: EnrollmentsComponent },
  { path: 'submissions', component: SubmissionsComponent }
];