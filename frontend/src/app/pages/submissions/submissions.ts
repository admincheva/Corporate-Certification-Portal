import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubmissionService } from '../../services/submission.service';
import { Submission } from '../../models/submission.model';
import { SubmissionFormComponent } from './submission-form.component';

@Component({
  selector: 'app-submissions',
  standalone: true,
  imports: [CommonModule, SubmissionFormComponent],
  templateUrl: './submissions.html',
  styleUrl: './submissions.css'
})
export class SubmissionsComponent implements OnInit {

  submissions: Submission[] = [];
  isLoading = false;
  showForm = false;
  username = '';

  constructor(private service: SubmissionService) {}

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    if (typeof window === 'undefined') return;

    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };

    if (!user.username) {
      this.submissions = [];
      return;
    }

    this.username = user.username;
    this.loadSubmissions();
  }

  loadSubmissions(): void {
    if (!this.username) return;

    this.isLoading = true;
    this.service.getMySubmissions(this.username).subscribe({
      next: data => {
        this.submissions = data;
        this.isLoading = false;
      },
      error: err => {
        console.error(err);
        this.isLoading = false;
      }
    });
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  onSubmissionSuccess(): void {
    this.showForm = false;
    this.loadSubmissions();
  }

  deleteSubmission(id: number): void {
    if (!confirm('Are you sure you want to delete this submission?')) {
      return;
    }

    this.service.delete(id).subscribe({
      next: () => {
        this.submissions = this.submissions.filter(s => s.id !== id);
      },
      error: err => {
        console.error('Error deleting submission:', err);
        alert('Failed to delete submission');
      }
    });
  }

}

