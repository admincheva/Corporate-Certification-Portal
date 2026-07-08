import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubmissionService } from '../../services/submission.service';
import { Submission } from '../../models/submission.model';

@Component({
  selector: 'app-submissions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './submissions.html'
})
export class SubmissionsComponent implements OnInit {

  submissions: Submission[] = [];

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

    this.service.getMySubmissions(user.username).subscribe({
      next: data => { this.submissions = data; },
      error: err => { console.error(err); }
    });
  }
}
