package ru.mondayish.services.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.tree.AnimalRoot;

@Service
public class AnimalRootService {

    private final AnimalRoot animalRoot;

    @Autowired
    public AnimalRootService(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    public AnimalRoot getAnimalRoot() {
        return animalRoot;
    }
}
