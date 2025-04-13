export interface User {
  uuid: string;
  username: string;
  wins: number;
  draws: number;
  losses: number;
  bets_won: number;
  bets_lost: number;
  elo: number;
  coins: number;
}

export interface UserCreds {
  username: string;
  password: string;
}
