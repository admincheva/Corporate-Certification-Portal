import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SubmissionService } from '../../services/submission.service';
import { Submission } from '../../models/submission.model';

@Component({
  selector: 'app-submissions',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './submissions.html',
  styleUrl: './submissions.css'
})
export class SubmissionsComponent implements OnInit {

  submissions: Submission[] = [];
  isLoading = false;

  constructor(private service: SubmissionService) {}

  ngOnInit(): void {
    this.loadSubmissions();
  }

  loadSubmissions() {
    if (typeof window === 'undefined') return;

    const user = JSON.parse(
      localStorage.getItem('user') || '{}'
    ) as { username?: string };

    if (!user.username) {
      this.submissions = [];
      return;
    }

    this.isLoading = true;
    this.service.getMySubmissions(user.username).subscribe({
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

  deleteSubmission(id: number) {
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
