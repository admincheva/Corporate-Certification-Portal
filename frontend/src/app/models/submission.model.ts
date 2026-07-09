export interface Submission {
  id?: number;
  username: string;
  enrollmentId: number;
  courseTitle: string;
  certificateFileUrl: string;
  invoiceFileUrl?: string;
  certificateName: string;
  issuingOrganization: string;
  issueDate: string;
  certificateUrl?: string;
  notes?: string;
  certificateNumber?: string;
  amountPaid?: number;
  status: string;
}