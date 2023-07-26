import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { PlayerRequest, PlayerResponse } from '../model/player.model';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private baseUrl = 'http://localhost:8081/players';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private messageService: MessageService) { }

  getPlayers(page: number, size: number): Observable<PlayerResponse[]> {
    return this.http.get<PlayerResponse[]>(`${this.baseUrl}?page=${page}&size=${size}`)
      .pipe(
        tap(_ => this.log('fetched players')),
        catchError(this.handleError<PlayerResponse[]>('getPlayers', []))
      );
  }

  getPlayer(id: number): Observable<PlayerResponse> {
    return this.http.get<PlayerResponse>(`${this.baseUrl}/${id}`)
      .pipe(
        tap(_ => this.log(`fetched player id=${id}`)),
        catchError(this.handleError<PlayerResponse>(`getPlayer id=${id}`))
      );
  }

  createPlayer(player: PlayerRequest): Observable<PlayerResponse> {
    return this.http.post<PlayerResponse>(this.baseUrl, player, this.httpOptions)
      .pipe(
        tap((newPlayer: PlayerResponse) => this
          .log(`added player w/ id=${newPlayer.id}`)),
        catchError(this.handleError<PlayerResponse>('createPlayer'))
      );
  }

  updatePlayer(id: number, player: PlayerRequest): Observable<PlayerResponse> {
    return this.http.put<PlayerResponse>(`${this.baseUrl}/${id}`, player, this.httpOptions)
      .pipe(
        tap(_ => this.log(`updated player id=${id}`)),
        catchError(this.handleError<PlayerResponse>('updatePlayer'))
      );
  }

  deletePlayer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.httpOptions)
      .pipe(
        tap(_ => this.log(`deleted player id=${id}`)),
        catchError(this.handleError<void>('deletePlayer'))
      );
  }

  transferPlayer(playerId: number, teamId: number): Observable<void> {
    return this.http
      .post<void>(`${this.baseUrl}/${playerId}/transfer`, { teamId }, this.httpOptions)
      .pipe(
        tap(_ => this.log(`transferred player id=${playerId} to team id=${teamId}`)),
        catchError(this.handleError<void>('transferPlayer'))
      );
  }

  initPlayers(players: PlayerRequest[]): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/init`, players, this.httpOptions)
      .pipe(
        tap((response: string) => this
          .log(`initialized players, server response: ${response}`)),
        catchError(this.handleError<string>('initPlayers'))
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
    this.messageService.add(`PlayerService: ${message}`);
  }
}
