import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { CoursesComponent } from './pages/courses/courses';
import { EnrollmentsComponent } from './pages/enrollments/enrollments';
import { SubmissionsComponent } from './pages/submissions/submissions';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'courses', component: CoursesComponent, canActivate: [AuthGuard] },
  { path: 'enrollments', component: EnrollmentsComponent, canActivate: [AuthGuard] },
  { path: 'submissions', component: SubmissionsComponent, canActivate: [AuthGuard] }
];