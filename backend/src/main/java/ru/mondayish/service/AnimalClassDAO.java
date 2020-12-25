package ru.mondayish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.AnimalClass;
import ru.mondayish.models.AnimalRoot;

@Service
public class AnimalClassDAO implements AnimalDAO<AnimalClass> {

    private final AnimalRoot animalRoot;

    @Autowired
    public AnimalClassDAO(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    @Override
    public AnimalClass addNode(AnimalClass node) {
        return null;
    }

    @Override
    public AnimalClass editNode(AnimalClass node) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void removeNode(long id) {

    }
}
