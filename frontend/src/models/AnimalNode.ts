export interface AnimalNode {
    name: string;
    population?: number;
    children?: AnimalNode[];
}