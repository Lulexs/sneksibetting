export interface Message {
    id: string;
}

export interface QueuedMessage extends Message {

}

export interface TypedMessage {
    id: string;
    message: Message;
}