import {Component, OnInit} from '@angular/core';
import { TeamService } from '../service/team.service';
import { TeamRequest, TeamResponse } from '../model/team.model';
import { faTimes } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {
  faTimes = faTimes;
  teams: TeamResponse[] = [];
  newTeam: TeamRequest = {} as TeamRequest;
  page: number = 0;
  size: number = 5;

  constructor(private teamService: TeamService) { }

  ngOnInit() {
    this.getTeams(this.page, this.size);
  }

  getTeams(page: number, size: number) {
    this.teamService.getTeams(page, size).subscribe((teams: TeamResponse[]) => {
      this.teams = teams;
    });
  }

  addTeam(): void {
    this.teamService.createTeam(this.newTeam)
      .subscribe(team => {
        this.teams.push(team);
        this.newTeam = {} as TeamRequest;
      });
  }

  deleteTeam(team: TeamResponse): void {
    this.teamService.deleteTeam(team.id).subscribe(() => {
      this.teams = this.teams.filter(t => t !== team);
    });
  }

  nextPage(): void {
    this.page++;
    this.getTeams(this.page, this.size);
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.getTeams(this.page, this.size);
    }
  }
}
