export interface Message {
    id: number;
}

export class QueuedMessage implements Message {
    id: number;
    
    constructor(id: number) {
        this.id = id;
    }
}

export interface TypedMessage {
    id: number;
    message: Message;
}