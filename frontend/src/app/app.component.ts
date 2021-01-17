import {FlatTreeControl} from '@angular/cdk/tree';
import {Component} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';

const TREE_HIERARCHY = ["type", "class", "squad", "family", "animal"];

const TREE_DATA: AnimalNode[] = [
    {
        id: 1,
        name: 'Spanches',
        numberOfKinds: 20000,
        children: [
            {
                id: 1,
                name: 'Spiders',
                numberOfKinds: 10000,
                children: [
                    {
                        id: 1,
                        name: 'Test1',
                        children: [
                            {
                                id: 1,
                                name: 'Test2',
                                children: [
                                    {
                                        id: 1,
                                        name: 'Test3',
                                        children: [
                                            {
                                                id: 1,
                                                name: 'Test4',
                                                children: []
                                            }
                                        ]
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    },
    {
        id: 2,
        name: 'testNotExpandable'
    }
];

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
})

export class AppComponent {

    private transformer(node: AnimalNode, level: number) {
        return {
            expandable: !!node.children && node.children.length > 0,
            name: node.name,
            level: level,
            numberOfKinds: node.numberOfKinds
        };
    }

    treeControl: FlatTreeControl<FlatAnimalNode> = new FlatTreeControl<FlatAnimalNode>(
        node => node.level, node => node.expandable);
    treeFlattener: MatTreeFlattener<AnimalNode, FlatAnimalNode> = new MatTreeFlattener(
        this.transformer, node => node.level, node => node.expandable, node => node.children);
    dataSource: MatTreeFlatDataSource<AnimalNode, FlatAnimalNode> = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
    selectedNode: FlatAnimalNode;

    constructor() {
        this.dataSource.data = TREE_DATA;
    }

    onNodeClick(node: FlatAnimalNode): void {
        this.selectedNode = node;
        console.log(node.level);
    }
}
