package ru.mondayish.services.nodes;

public interface AnimalNodesDAO<T> {

    T addNode(T node, long parentId) throws IllegalArgumentException;

    void editNode(T node, long parentId) throws IllegalArgumentException;

    void removeNode(long id, long parentId) throws IllegalArgumentException;
}
