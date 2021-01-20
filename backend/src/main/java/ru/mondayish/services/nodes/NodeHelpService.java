package ru.mondayish.services.nodes;

import ru.mondayish.models.node.Node;

import java.util.Optional;

/**
 * Additional methods to prevent duplicated code
 */
public class NodeHelpService {

    /**
     * For editing node without children field
     *
     * @param parent parent node
     * @param node   node with edited fields
     */
    public static boolean editNodeWithoutChildren(Node parent, Node node) {
        Optional<Node> currentNode = parent
                .getChildren()
                .stream()
                .filter(child -> child.getId() == node.getId())
                .findAny();
        currentNode.ifPresent(existingNode -> {
            parent.removeChild(node.getId());
            node.setChildren(existingNode.getChildren());
            parent.addChild(node);
        });
        return currentNode.isPresent();
    }
}
