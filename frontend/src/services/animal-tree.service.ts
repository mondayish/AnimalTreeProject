import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Level} from '../models/Level';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';

@Injectable({
    providedIn: 'root'
})
export class AnimalTreeService {

    private static readonly TREE_URL: string = 'http://localhost:8080/tree/';
    private static readonly NAMES_BY_LEVEL: Map<Level, string> = new Map([
        [Level.ROOT, 'root'],
        [Level.TYPE, 'type'],
        [Level.CLASS, 'class'],
        [Level.SQUAD, 'squad'],
        [Level.FAMILY, 'family'],
        [Level.CONCRETE_ANIMAL, 'animal'],
    ]);


    constructor(private httpClient: HttpClient) {
    }

    getTree(): Observable<any> {
        return this.httpClient.get(AnimalTreeService.TREE_URL + AnimalTreeService.NAMES_BY_LEVEL.get(Level.ROOT));
    }

    addChild(node: AnimalNode, level: Level, parentId: number){
        this.httpClient.post(node)
    }
}
