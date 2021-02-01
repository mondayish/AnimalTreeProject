import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Level} from '../models/Level';
import {AnimalNode} from '../models/AnimalNode';

@Injectable({
    providedIn: 'root'
})
export class AnimalTreeService {

    private static readonly TREE_URL: string =
        'http://localhost:8080' +
        '/tree/';
    private static readonly STREAM_URL: string =
        'http://localhost:8080' +
        '/tree/root/stream';
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

    addNode(node: AnimalNode, level: Level, parentId: number): Observable<any> {
        const url: string = AnimalTreeService.TREE_URL + (level === Level.TYPE ? AnimalTreeService.NAMES_BY_LEVEL.get(Level.TYPE) :
            AnimalTreeService.NAMES_BY_LEVEL.get(level - 1) + '/' + parentId + '/' + AnimalTreeService.NAMES_BY_LEVEL.get(level));
        return this.httpClient.post(url, node);
    }

    editNode(node: AnimalNode, level: Level, parentId: number): Observable<any> {
        const url: string = AnimalTreeService.TREE_URL + (level === Level.TYPE ? AnimalTreeService.NAMES_BY_LEVEL.get(Level.TYPE) :
            AnimalTreeService.NAMES_BY_LEVEL.get(level - 1) + '/' + parentId + '/' + AnimalTreeService.NAMES_BY_LEVEL.get(level));
        return this.httpClient.put(url, node);
    }

    deleteNode(node: AnimalNode, level: Level, parentId: number): Observable<any> {
        const url: string = AnimalTreeService.TREE_URL + (level === Level.TYPE ? AnimalTreeService.NAMES_BY_LEVEL.get(Level.TYPE) :
            AnimalTreeService.NAMES_BY_LEVEL.get(level - 1) + '/' + parentId + '/' + AnimalTreeService.NAMES_BY_LEVEL.get(level))
            + '/' + node.id;
        return this.httpClient.delete(url);
    }

    subscribeOnEvents(onmessage: (message) => void, onerror: (error) => void) {
        const source = new EventSource(AnimalTreeService.STREAM_URL);
        source.addEventListener('message', onmessage);
        source.addEventListener('error', onerror);
    }
}
