import {FlatTreeControl} from '@angular/cdk/tree';
import {Component, ViewChild} from '@angular/core';
import {MatTree, MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';
import {Action} from '../models/Action';
import {Notification} from '../models/Notification';
import {FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Level} from '../models/Level';
import {AnimalTreeService} from '../services/animal-tree.service';


@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css'],
})

export class AppComponent {

    static nodeMap: Map<FlatAnimalNode, AnimalNode> = new Map<FlatAnimalNode, AnimalNode>();

    @ViewChild(MatTree) matTree: MatTree<any>;
    rootTitle: string = 'Animals';
    treeData: AnimalNode[];
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
        this.subscribeOnEvents();
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
            .subscribe(() => console.log('Delete successful'), () => console.log('Error after http request'));
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
            .subscribe(() => console.log('Add successful'), () => console.log('Error after http request'));
        this.action = Action.NONE;
        this.resetForm();
    }

    onFormEditClick(): void {
        const parentNode: FlatAnimalNode = this.getParent(this.selectedNode);
        this.animalTreeService.editNode(this.actionNode, this.selectedNode.level, parentNode.id)
            .subscribe(() => console.log('Edit successful'), () => console.log('Error after http request'));
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

    private subscribeOnEvents(): void {
        this.animalTreeService.subscribeOnEvents(
            message => {
                const notification: Notification = <Notification> JSON.parse(message.data);
                console.log(notification);
                console.log(notification.requestMethod);
                switch (notification.requestMethod) {
                    case 'POST':
                        const parent: AnimalNode = this.findNodeByIdAndLevel(notification.parentId, notification.level - 1);
                        parent.children.push(notification.result);
                        this.updateTreeData();
                        break;
                    case 'PUT':
                        const nodeToEdit: AnimalNode = this.findNodeByIdAndLevel(notification.result.id, notification.level);
                        nodeToEdit.name = notification.result.name;
                        nodeToEdit.numberOfKinds = notification.result.numberOfKinds;
                        this.updateTreeData();
                        break;
                    case 'DELETE':
                        this.deleteNodeByIdAndLevel(notification.deletedId, notification.level);
                        this.updateTreeData();
                        break;
                    default:
                        console.log('This http method is not supported now');
                }
            },
            () => console.log('Server-sent emitter timeout ended. Refresh page to reconnect.')
        );
    }

    private findNodeByIdAndLevel(id: number, level: number): AnimalNode {
        const nodes: AnimalNode[][] = [this.treeData];
        for (let i = 1; i < 6; i++) {
            const nodesOnLevel: AnimalNode[] = [];
            nodes[i - 1].forEach(nodes => {
                if (nodes.children !== undefined) {
                    nodes.children.forEach(child => nodesOnLevel.push(child));
                }
            });
            nodes.push(nodesOnLevel);
        }
        return nodes[level].find(node => node.id === id);
    }

    private deleteNodeByIdAndLevel(id: number, level: number): void {
        const nodes: AnimalNode[][] = [this.treeData];
        for (let i = 1; i < level; i++) {
            const nodesOnLevel: AnimalNode[] = [];
            nodes[i - 1].forEach(nodes => {
                if (nodes.children !== undefined) {
                    nodes.children.forEach(child => nodesOnLevel.push(child));
                }
            });
            nodes.push(nodesOnLevel);
        }
        for (let node of nodes[level - 1]) {
            const nodeToDelete: AnimalNode = node.children.find(n => n.id === id);
            if (nodeToDelete !== undefined) {
                console.log('Found');
                node.children.splice(node.children.indexOf(nodeToDelete), 1);
                break;
            }
        }
    }

    private updateTreeData(): void {
        this.dataSource.data = this.treeData;
        this.treeControl.expandAll();
    }

    private loadTree(): void {
        this.animalTreeService.getTree()
            .subscribe((data: AnimalNode) => {
                this.treeData = [data];
                this.updateTreeData();
            });
    }

    private resetForm(): void {
        this.actionForm.reset();
    }

    private transformer(node: AnimalNode, level: number): FlatAnimalNode {
        const flatAnimalNode: FlatAnimalNode = {
            id: node.id,
            expandable: !!node.children && node.children.length > 0,
            name: node.name,
            level: level,
            numberOfKinds: node.numberOfKinds
        };
        AppComponent.nodeMap.set(flatAnimalNode, node);
        return flatAnimalNode;
    }
}
