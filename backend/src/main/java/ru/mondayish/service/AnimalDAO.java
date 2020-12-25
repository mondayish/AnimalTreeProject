package ru.mondayish.service;

public interface AnimalDAO<T> {

    T addNode(T node);

    T editNode(T node) throws IllegalArgumentException;

    void removeNode(long id);
}
