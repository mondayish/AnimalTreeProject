import {FlatTreeControl} from '@angular/cdk/tree';
import {Component} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';
import {Action} from '../models/Action';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Level} from '../models/Level';
import {AnimalTreeService} from '../services/animal-tree.service';

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
    actionNode: AnimalNode;
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

    constructor(private animalTreeService: AnimalTreeService) {
        this.loadTree();
    }

    onNodeClick(node: FlatAnimalNode): void {
        this.action = Action.NONE;
        this.resetForm();
        this.selectedNode = node;
    }

    // only animal types and classes have this property
    isNodeHasKindsOfSpecies(node: FlatAnimalNode): boolean {
        return node.level === Level.TYPE || node.level === Level.CLASS;
    }

    // concreteAnimal can't has a child
    isNodeCantHasChild(node: FlatAnimalNode): boolean {
        return node.level === Level.CONCRETE_ANIMAL;
    }

    // if node hasn't name it's root node
    isNodeRoot(node: FlatAnimalNode): boolean {
        return node.level === Level.ROOT;
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

    onAddClick(): void {
        this.actionNode = {id: 0, name: this.selectedNode.name, numberOfKinds: this.selectedNode.numberOfKinds};
        this.action = Action.ADD;
    }

    onEditClick(): void {
        this.actionNode = {id: this.selectedNode.id, name: this.selectedNode.name, numberOfKinds: this.selectedNode.numberOfKinds};
        this.action = Action.EDIT;
    }

    onDeleteClick(): void {
        this.action = Action.DELETE;
    }

    fieldErrors(field: string): ValidationErrors | null {
        const fieldState = this.actionForm.controls[field];
        return fieldState.dirty && fieldState.errors ? fieldState.errors : null;
    }

    hasFormErrors(): boolean {
        return this.actionForm.invalid;
    }

    onCancelClick(): void {
        this.resetForm();
        this.action = Action.NONE;
    }

    onFormSubmit(): void {
        this.resetForm();
    }

    isActionNodeWithNumber(): boolean {
        return this.isAddAction() && (this.selectedNode.level === Level.ROOT || this.selectedNode.level === Level.TYPE)
            || this.isEditAction() && this.isNodeHasKindsOfSpecies(this.selectedNode);
    }

    private loadTree(): void {
        this.animalTreeService.getTree()
            .subscribe((data: AnimalNode) => {
                this.dataSource.data = [data];
            });
    }

    private resetForm(): void {
        this.actionForm.reset();
    }
}
