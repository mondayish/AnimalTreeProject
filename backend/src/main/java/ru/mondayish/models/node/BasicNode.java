package ru.mondayish.models.node;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNode implements Node {

    private List<Node> children = new ArrayList<>();

    @Override
    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void removeChild(long id) {
        children.removeIf(child -> child.getId() == id);
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setChildren(List<Node> children) {
        this.children = new ArrayList<>(children);
    }
}
