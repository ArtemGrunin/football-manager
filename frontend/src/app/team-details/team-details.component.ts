import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PlayerResponse} from "../model/player.model";
import {PlayerService} from "../service/player.service";
import {TeamResponse} from "../model/team.model";
import {TeamService} from "../service/team.service";

@Component({
  selector: 'app-team-details',
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.css']
})
export class TeamDetailsComponent implements OnInit {
  team: TeamResponse = {} as TeamResponse;
  players: PlayerResponse[] = [];
  private teamId: number | undefined;
  editMode = false;

  constructor(private playerService: PlayerService,
              private teamService: TeamService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = +params['id'];
      if(this.teamId){
        this.getPlayersByTeamId(this.teamId);
        this.getTeamById(this.teamId);
      }
    });
  }

  getPlayersByTeamId(teamId: number): void {
    this.teamService
      .getPlayersByTeamId(teamId).subscribe(players => this.players = players);
  }

  getTeamById(teamId: number) {
    this.teamService.getTeam(Number(teamId))
      .subscribe(team => this.team = team);
  }

  save(): void {
    this.teamService.updateTeam(this.team.id, this.team)
      .subscribe(() => this.editMode = false);
  }

  cancel(): void {
    this.editMode = false;
  }
}
