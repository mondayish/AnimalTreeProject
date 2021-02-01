import {AnimalNode} from '../models/AnimalNode';
import {FlatAnimalNode} from '../models/FlatAnimalNode';

export class TreeUtil {

    private static MAX_LEVEl: number = 5;

    static findNodeByIdAndLevel(treeData: AnimalNode[], id: number, level: number): AnimalNode {
        const nodes: AnimalNode[][] = [treeData];
        for (let i = 1; i < this.MAX_LEVEl + 1; i++) {
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

    static deleteNodeByIdAndLevel(treeData: AnimalNode[], id: number, level: number): void {
        const nodes: AnimalNode[][] = [treeData];
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
                node.children.splice(node.children.indexOf(nodeToDelete), 1);
                break;
            }
        }
    }

    static getParent(dataNodes: FlatAnimalNode[], node: FlatAnimalNode): FlatAnimalNode | null {
        const currentLevel = node.level;
        if (currentLevel < 1) {
            return null;
        }
        const startIndex = dataNodes.indexOf(node) - 1;
        for (let i = startIndex; i >= 0; i--) {
            const currentNode = dataNodes[i];
            if (currentNode.level < currentLevel) {
                return currentNode;
            }
        }
    }
}
