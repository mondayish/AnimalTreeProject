package ru.mondayish.services.root;

public interface AnimalRootDAO<T> {

    T addNode(T node);

    boolean editNode(T node);

    void removeNode(long id);
}
