export interface Message {}

export interface QueuedMessage extends Message {}

export interface GameStartNoBets extends Message {
  gameId: string;
  player1Board: number[][];
  player2Board: number[][];
  player1HeadPosI: number;
  player1HeadPosJ: number;
  player2HeadPosI: number;
  player2HeadPosJ: number;
  player1Username: string;
  player2Username: string;
  player1Elo: number;
  player2Elo: number;
  player1Score: number;
  player2Score: number;
  eloIfP1Wins: number;
  eloIfP1Lose: number;
  eloIfDraw: number;
  numWatching: number;
}

export interface UpdateGameStateMessage extends Message {
  gameId: string;
  player1Board: number[][];
  player2Board: number[][];
  player1HeadPosI: number;
  player1HeadPosJ: number;
  player2HeadPosI: number;
  player2HeadPosJ: number;
  player1Score: number;
  player2Score: number;
  numWatching: number;
  player1Username: string;
  player2Username: string;
}

export interface TypedMessage {
  id: number;
  message: Message;
}
