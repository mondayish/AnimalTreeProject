package ru.mondayish.models.node;

import java.util.List;

public interface Node {

    long getId();

    void addChild(Node child);

    void removeChild(long id);

    List<Node> getChildren();
}
