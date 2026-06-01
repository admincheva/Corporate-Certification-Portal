import { User } from './user.model';
import { Enrollment } from './enrollment.model';

export interface Submission {
  id?: number;
  user: User;
  enrollment: Enrollment;
  certificateFileUrl: string;
  invoiceFileUrl: string;
  certificateNumber: string;
  amountPaid: number;
  status: string;
}