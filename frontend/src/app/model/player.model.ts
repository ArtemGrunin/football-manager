export interface PlayerRequest {
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  careerStartDate: Date;
  experienceMonths: number;
  teamId: number;
}

export interface PlayerResponse {
  id: number;
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  careerStartDate: Date;
  experienceMonths: number;
  age: number;
  teamId: number;
}
