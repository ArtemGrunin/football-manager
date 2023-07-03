import {Component, OnInit} from '@angular/core';
import {PlayerService} from "../service/player.service";
import {PlayerRequest, PlayerResponse} from "../model/player.model";
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import {TeamService} from "../service/team.service";
import {TeamResponse} from "../model/team.model";

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.css']
})
export class PlayersComponent implements OnInit {
  faTimes = faTimes;
  players: PlayerResponse[] = [];
  teams: TeamResponse[] = [];
  newPlayer: PlayerRequest = {} as PlayerRequest;
  page: number = 0;
  size: number = 5;

  constructor(private playerService: PlayerService, private teamService: TeamService) { }

  ngOnInit() {
    this.getPlayers(this.page, this.size);
    this.getTeams(0, 100);
  }

  addPlayer(): void {
    this.playerService.createPlayer(this.newPlayer)
      .subscribe(player => {
        this.players.push(player);
        this.newPlayer = {} as PlayerRequest;
      });
  }

  getPlayers(page: number, size: number) {
    this.playerService.getPlayers(page, size).subscribe((players: PlayerResponse[]) => {
      this.players = players;
    });
  }

  deletePlayer(player: PlayerResponse): void {
    this.playerService.deletePlayer(player.id).subscribe(() => {
      this.players = this.players.filter(t => t !== player);
    });
  }

  nextPage(): void {
    this.page++;
    this.getPlayers(this.page, this.size);
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.getPlayers(this.page, this.size);
    }
  }

  getTeams(page: number, size: number) {
    this.teamService.getTeams(page, size).subscribe((teams: TeamResponse[]) => {
      this.teams = teams;
    });
  }

  getTeamName(teamId: number): string {
    const team = this.teams.find(team => team.id === teamId);
    return team ? team.name : 'Unknown';
  }
}
