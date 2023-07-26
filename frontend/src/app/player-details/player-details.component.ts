import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PlayerService } from '../service/player.service';
import { TeamService } from '../service/team.service';
import { PlayerResponse, PlayerRequest } from '../model/player.model'; // Imported PlayerRequest
import { TeamResponse } from '../model/team.model';
import { FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: 'app-player-details',
  templateUrl: './player-details.component.html',
  styleUrls: ['./player-details.component.css']
})
export class PlayerDetailsComponent implements OnInit {
  editMode = false;
  transferForm?: FormGroup;
  player: PlayerResponse = {} as PlayerResponse;
  originalPlayer: PlayerResponse = {} as PlayerResponse;
  editedCareerStartDate: string = '';
  editedDateOfBirth: string = '';
  teams: TeamResponse[] = [];
  page: number = 0;
  size: number = 100;

  constructor(
    private route: ActivatedRoute,
    private playerService: PlayerService,
    private teamService: TeamService
  ) {}

  ngOnInit() {
    this.getTeams(this.page, this.size);
    this.getPlayer();
    this.transferForm = new FormGroup({
      teamId: new FormControl(null)
    });
  }

  getTeams(page: number, size: number) {
    this.teamService.getTeams(page, size)
      .subscribe((teams: TeamResponse[]) => {
        this.teams = teams;
      });
  }

  getPlayer(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.playerService.getPlayer(id)
      .subscribe(player => {
        this.player = player;
        this.editedCareerStartDate = new Date(player.careerStartDate).toISOString().split('T')[0];
      });
  }

  onTransfer(): void {
    let teamId = this.transferForm?.value.teamId;
    if (teamId === null || teamId === undefined) {
      alert('Please select a team');
      return;
    }
    teamId = Number(teamId);
    if (this.player.teamId === null) {
      alert('Player does not belong to any team');
    } else if (this.player.teamId !== teamId) {
      this.playerService.transferPlayer(this.player.id, teamId)
        .subscribe({
          next: () => {
            alert('Player has been transferred successfully!');
            this.getPlayer();
          },
          error: err => {
            console.error('There was an error transferring the player', err);
          }
        });
    } else {
      alert('Player is already in this team');
    }
  }

  getTeamName(teamId: number): string {
    const team = this.teams.find(team => team.id === teamId);
    return team ? team.name : 'Unknown';
  }

  save(): void {
    this.playerService.updatePlayer(this.player.id, this.player).subscribe({
      next: updatedPlayerResponse => {
        this.player = updatedPlayerResponse;
        this.editMode = false;
      },
      error: error => {
        console.error("There was an error updating the player", error);
        this.player = { ...this.originalPlayer };
        this.editMode = false;
      }
    });
  }

  cancel(): void {
    this.editMode = false;
    this.getPlayer();
  }
}
