import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { TeamRequest, TeamResponse } from '../model/team.model';
import {MessageService} from "./message.service";
import {PlayerResponse} from "../model/player.model";

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private baseUrl = 'http://localhost:8081/teams';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private messageService: MessageService) { }

  getTeams(page: number, size: number): Observable<TeamResponse[]> {
    return this.http.get<TeamResponse[]>(`${this.baseUrl}?page=${page}&size=${size}`)
      .pipe(
        tap(_ => this.log('fetched teams')),
        catchError(this.handleError<TeamResponse[]>('getTeams', []))
      );
  }

  getTeam(id: number): Observable<TeamResponse> {
    return this.http.get<TeamResponse>(`${this.baseUrl}/${id}`)
      .pipe(
        tap(_ => this.log(`fetched team id=${id}`)),
        catchError(this.handleError<TeamResponse>(`getTeam id=${id}`))
      );
  }

  createTeam(team: TeamRequest): Observable<TeamResponse> {
    return this.http.post<TeamResponse>(this.baseUrl, team, this.httpOptions)
      .pipe(
        tap((newTeam: TeamResponse) => this.log(`added team w/ id=${newTeam.id}`)),
        catchError(this.handleError<TeamResponse>('createTeam'))
      );
  }

  updateTeam(id: number, team: TeamRequest): Observable<TeamResponse> {
    return this.http.put<TeamResponse>(`${this.baseUrl}/${id}`, team, this.httpOptions)
      .pipe(
        tap(_ => this.log(`updated team id=${id}`)),
        catchError(this.handleError<TeamResponse>('updateTeam'))
      );
  }

  deleteTeam(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.httpOptions)
      .pipe(
        tap(_ => this.log(`deleted team id=${id}`)),
        catchError(this.handleError<void>('deleteTeam'))
      );
  }

  getPlayersByTeamId(teamId: number): Observable<PlayerResponse[]> {
    return this.http.get<PlayerResponse[]>(`${this.baseUrl}/${teamId}/players`)
      .pipe(
        tap(_ => this.log(`fetched players for team id=${teamId}`)),
        catchError(this.handleError<PlayerResponse[]>('getPlayersByTeamId', []))
      );
  }

  initTeams(teams: TeamRequest[]): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/init`, teams, this.httpOptions)
      .pipe(
        tap((response: string) => this
          .log(`initialized teams, server response: ${response}`)),
        catchError(this.handleError<string>('initTeams'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`TeamService: ${message}`);
  }
}
