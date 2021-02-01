package ru.mondayish.services.nodes;

public interface AnimalNodesDAO<T> {

    T addNode(T node, Long parentId) throws IllegalArgumentException;

    void editNode(T node, Long parentId) throws IllegalArgumentException;

    void removeNode(Long id, Long parentId) throws IllegalArgumentException;
}
