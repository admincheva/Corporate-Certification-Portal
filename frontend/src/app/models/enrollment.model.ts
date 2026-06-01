import { User } from './user.model';
import { Course } from './course.model';

export interface Enrollment {
  id?: number;
  user: User;
  course: Course;
  status: string;
}