export interface Submission {
  id?: number;
  username: string;
  enrollmentId: number;
  courseTitle: string;
  certificateFileUrl: string;
  invoiceFileUrl: string;
  certificateNumber: string;
  amountPaid: number;
  status: string;
}