import {AnimalNode} from './AnimalNode';

export interface Notification {
    requestMethod: string,
    level: number,
    result?: AnimalNode;
    parentId?: number;
    deletedId?: number;
}
