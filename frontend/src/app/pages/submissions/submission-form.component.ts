import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { EnrollmentService, EnrollmentSummary } from '../../services/enrollment.service';
import { SubmissionService } from '../../services/submission.service';
import { Submission } from '../../models/submission.model';

@Component({
  selector: 'app-submission-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './submission-form.component.html',
  styleUrl: './submission-form.component.css'
})
export class SubmissionFormComponent implements OnInit {

  @Output() submissionSuccess = new EventEmitter<void>();
  @Input() username: string = '';

  form: FormGroup;
  enrollments: EnrollmentSummary[] = [];
  isLoading = false;
  isSubmitting = false;
  selectedFileName = '';
  successMessage = '';
  errorMessage = '';

  private readonly MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
  private readonly ALLOWED_TYPES = ['application/pdf', 'image/jpeg', 'image/png'];
  private readonly ALLOWED_EXTENSIONS = ['.pdf', '.jpg', '.jpeg', '.png'];

  constructor(
    private fb: FormBuilder,
    private enrollmentService: EnrollmentService,
    private submissionService: SubmissionService
  ) {
    this.form = this.fb.group({
      enrollmentId: ['', Validators.required],
      certificateName: ['', Validators.required],
      issuingOrganization: ['', Validators.required],
      issueDate: ['', Validators.required],
      certificateUrl: [''],
      notes: [''],
      certificateFile: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments(): void {
    if (!this.username) return;

    this.isLoading = true;
    this.enrollmentService.getCurrentUserEnrollments(this.username).subscribe({
      next: (data) => {
        this.enrollments = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading enrollments:', err);
        this.isLoading = false;
        this.errorMessage = 'Failed to load your courses. Please try again.';
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) {
      this.selectedFileName = '';
      this.form.patchValue({ certificateFile: '' });
      return;
    }

    // Validate file type
    if (!this.ALLOWED_TYPES.includes(file.type) && !this.isAllowedExtension(file.name)) {
      this.errorMessage = 'Invalid file type. Please upload a PDF, JPG, JPEG, or PNG file.';
      input.value = '';
      this.form.get('certificateFile')?.markAsTouched();
      return;
    }

    // Validate file size
    if (file.size > this.MAX_FILE_SIZE) {
      this.errorMessage = `File size exceeds 5MB limit. Your file is ${(file.size / 1024 / 1024).toFixed(2)}MB.`;
      input.value = '';
      this.form.get('certificateFile')?.markAsTouched();
      return;
    }

    this.selectedFileName = file.name;
    this.form.patchValue({ certificateFile: file });
    this.errorMessage = '';
  }

  private isAllowedExtension(filename: string): boolean {
    const ext = filename.toLowerCase().substring(filename.lastIndexOf('.'));
    return this.ALLOWED_EXTENSIONS.includes(ext);
  }

  getFileInputId(): string {
    return 'certificateFileInput';
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields.';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    const formData = new FormData();
    const enrollmentId = this.form.get('enrollmentId')?.value;
    const file = this.form.get('certificateFile')?.value;

    formData.append('username', this.username);
    formData.append('enrollmentId', enrollmentId);
    formData.append('certificateName', this.form.get('certificateName')?.value);
    formData.append('issuingOrganization', this.form.get('issuingOrganization')?.value);
    formData.append('issueDate', this.form.get('issueDate')?.value);
    formData.append('certificateUrl', this.form.get('certificateUrl')?.value || '');
    formData.append('notes', this.form.get('notes')?.value || '');
    formData.append('certificateFile', file);

    this.submissionService.createWithFile(formData).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.successMessage = 'Certificate submitted successfully! 🎉';
        this.form.reset();
        this.selectedFileName = '';

        // Emit success and reload list after 2 seconds
        setTimeout(() => {
          this.submissionSuccess.emit();
        }, 2000);
      },
      error: (err) => {
        this.isSubmitting = false;
        console.error('Error submitting certificate:', err);
        this.errorMessage = err.error?.message || 'Failed to submit certificate. Please try again.';
      }
    });
  }

  cancel(): void {
    this.form.reset();
    this.selectedFileName = '';
    this.errorMessage = '';
    this.successMessage = '';
  }

}
