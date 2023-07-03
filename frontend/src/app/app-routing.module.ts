import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeamsComponent } from './teams/teams.component';
import { PlayersComponent } from './players/players.component';
import {TeamDetailsComponent} from "./team-details/team-details.component";
import {PlayerDetailsComponent} from "./player-details/player-details.component";

const routes: Routes = [
  { path: 'teams', component: TeamsComponent },
  { path: 'teams/:id/players', component: TeamDetailsComponent },
  { path: 'players', component: PlayersComponent },
  { path: 'players/:id', component: PlayerDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
