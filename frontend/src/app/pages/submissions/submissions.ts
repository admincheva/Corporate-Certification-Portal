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
    this.service.getAll().subscribe(data => {
      this.submissions = data;
    });
  }
}
