import {FlatTreeControl} from '@angular/cdk/tree';
import {Component} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';
import {Action} from '../models/Action';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {resolveTxt} from 'dns';

const TREE_HIERARCHY = ['root', 'type', 'class', 'squad', 'family', 'animal'];

const TREE_DATA: AnimalNode[] = [
    {
        id: 0,
        children: [{
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
        }]
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
            id: node.id,
            expandable: !!node.children && node.children.length > 0,
            name: node.name,
            level: level,
            numberOfKinds: node.numberOfKinds
        };
    }

    rootTitle: string = 'Animals';
    actionNode: AnimalNode = {id: 0, name: '', numberOfKinds: 0};
    actionForm: FormGroup = new FormGroup({
        name: new FormControl('',
            [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
        numberOfKinds: new FormControl('', [Validators.required, Validators.min(1)])
    });
    action: Action = Action.NONE;
    selectedNode: FlatAnimalNode;
    treeControl: FlatTreeControl<FlatAnimalNode> = new FlatTreeControl<FlatAnimalNode>(
        node => node.level, node => node.expandable);
    treeFlattener: MatTreeFlattener<AnimalNode, FlatAnimalNode> = new MatTreeFlattener(
        this.transformer, node => node.level, node => node.expandable, node => node.children);
    dataSource: MatTreeFlatDataSource<AnimalNode, FlatAnimalNode> = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    constructor() {
        this.dataSource.data = TREE_DATA;
    }

    onNodeClick(node: FlatAnimalNode): void {
        this.selectedNode = node;
    }

    // only animal types and classes have this property
    isNodeHasKindsOfSpecies(node: FlatAnimalNode): boolean {
        return node.level < 3 && node.level > 0;
    }

    // concreteAnimal can't has a child
    isNodeCantHasChild(node: FlatAnimalNode): boolean {
        return node.level > 4;
    }

    // if node hasn't name it's root node
    isNodeRoot(node: FlatAnimalNode): boolean {
        return node.name === undefined;
    }

    isAddAction(): boolean {
        return this.action === Action.ADD;
    }

    isEditAction(): boolean {
        return this.action === Action.EDIT;
    }

    isDeleteAction(): boolean {
        return this.action === Action.DELETE;
    }

    isActionNodeWithoutNumber(): boolean {
        return this.isAddAction() && this.selectedNode.level > 0 || this.isEditAction() && this.selectedNode.level > 1;
    }
}
