export interface Message {}

export interface QueuedMessage extends Message {}

export interface GameStartNoBets {}

export interface TypedMessage {
  id: number;
  message: Message;
}
