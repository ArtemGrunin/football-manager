import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { TeamsComponent } from './teams/teams.component';
import { PlayersComponent } from './players/players.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {TeamService} from "./service/team.service";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {PlayerService} from "./service/player.service";
import { TeamDetailsComponent } from './team-details/team-details.component';
import { PlayerDetailsComponent } from './player-details/player-details.component';

@NgModule({
  declarations: [
    AppComponent,
    TeamsComponent,
    PlayersComponent,
    TeamDetailsComponent,
    PlayerDetailsComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [TeamService, PlayerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
