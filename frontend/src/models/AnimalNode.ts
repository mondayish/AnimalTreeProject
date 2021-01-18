export interface AnimalNode {
    id: number,
    name?: string;
    numberOfKinds?: number;
    children?: AnimalNode[];
}
