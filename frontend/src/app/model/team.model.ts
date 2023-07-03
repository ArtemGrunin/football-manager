export interface TeamRequest {
  name: string;
  city: string;
  country: string;
  budget: number;
  transferCommission: number;
}

export interface TeamResponse {
  id: number;
  name: string;
  city: string;
  country: string;
  budget: number;
  transferCommission: number;
}
