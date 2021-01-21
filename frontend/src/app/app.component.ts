import {FlatTreeControl} from '@angular/cdk/tree';
import {Component} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';
import {Action} from '../models/Action';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Level} from '../models/Level';
import {AnimalTreeService} from '../services/animal-tree.service';


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
        const source = new EventSource('/tree/root/stream');
        source.addEventListener('message', message => {
            console.log(message);
            this.dataSource.data = [JSON.parse(message.data)];
        });
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

    onAddClick(): void {
        this.actionNode = {id: 0, name: '', numberOfKinds: this.isActionNodeWithNumber() ? 0 : undefined};
        this.action = Action.ADD;
    }

    onEditClick(): void {
        this.actionNode = {id: this.selectedNode.id, name: this.selectedNode.name, numberOfKinds: this.selectedNode.numberOfKinds};
        this.action = Action.EDIT;
    }

    onDeleteClick(): void {
        const nodeToDelete: AnimalNode = {
            id: this.selectedNode.id,
            name: this.selectedNode.name,
            numberOfKinds: this.selectedNode.numberOfKinds
        };
        const parentNode: FlatAnimalNode = this.getParent(this.selectedNode);
        this.animalTreeService.deleteNode(nodeToDelete, this.selectedNode.level, parentNode.id)
            .subscribe((value) => this.loadTree(), (error) => console.log('Error after http request'));
        this.action = Action.NONE;
    }

    fieldErrors(field: string): ValidationErrors | null {
        const fieldState = this.actionForm.controls[field];
        return fieldState.dirty && fieldState.errors ? fieldState.errors : null;
    }

    // because sometimes we need to check only first field
    hasFormErrors(): boolean {
        return this.actionForm.invalid;
    }

    // difficult to explain...
    isNumberFieldDisabledAndNameIsOk(): boolean {
        return !this.isActionNodeWithNumber() && !this.fieldErrors('name')
            && (this.actionForm.controls['name'].dirty || this.action === Action.EDIT);
    }

    onCancelClick(): void {
        this.resetForm();
        this.action = Action.NONE;
    }

    onFormAddClick(): void {
        this.animalTreeService.addNode(this.actionNode, this.selectedNode.level + 1, this.selectedNode.id)
            .subscribe((value) => this.loadTree(), (error) => console.log('Error after http request'));
        this.action = Action.NONE;
        this.resetForm();
    }

    onFormEditClick(): void {
        const parentNode: FlatAnimalNode = this.getParent(this.selectedNode);
        this.animalTreeService.editNode(this.actionNode, this.selectedNode.level, parentNode.id)
            .subscribe((value) => this.loadTree(), (error) => console.log('Error after http request'));
        this.action = Action.NONE;
        this.resetForm();
    }

    isActionNodeWithNumber(): boolean {
        return this.isAddAction() && (this.selectedNode.level === Level.ROOT || this.selectedNode.level === Level.TYPE)
            || this.isEditAction() && this.isNodeHasKindsOfSpecies(this.selectedNode);
    }

    private getParent(node: FlatAnimalNode): FlatAnimalNode | null {
        const currentLevel = node.level;
        if (currentLevel < 1) {
            return null;
        }
        const startIndex = this.treeControl.dataNodes.indexOf(node) - 1;
        for (let i = startIndex; i >= 0; i--) {
            const currentNode = this.treeControl.dataNodes[i];
            if (currentNode.level < currentLevel) {
                return currentNode;
            }
        }
    }

    private loadTree(): void {
        this.animalTreeService.getTree()
            .subscribe((data: AnimalNode) => {
                this.dataSource.data = [data];
                this.treeControl.expandAll();
            });
    }

    private resetForm(): void {
        this.actionForm.reset();
    }
}
