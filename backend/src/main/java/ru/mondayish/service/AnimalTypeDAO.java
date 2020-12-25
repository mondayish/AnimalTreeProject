package ru.mondayish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.AnimalRoot;
import ru.mondayish.models.AnimalType;
import ru.mondayish.utils.IdGenerator;

@Service
public class AnimalTypeDAO implements AnimalDAO<AnimalType> {

    private final AnimalRoot animalRoot;

    @Autowired
    public AnimalTypeDAO(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    @Override
    public AnimalType addNode(AnimalType animalType) {
        animalType.setId(IdGenerator.getTypeId());
        animalRoot.addChild(animalType);
        return animalType;
    }

    @Override
    public AnimalType editNode(AnimalType node) throws IllegalArgumentException {
        long childId = animalRoot
                .getChildren()
                .stream()
                .filter(child -> child.getId() == node.getId()).findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getId();
        animalRoot.removeChild(childId);
        AnimalType editedAnimalType = new AnimalType(childId, node.getName(), node.getNumberOfKinds());
        animalRoot.addChild(editedAnimalType);
        return editedAnimalType;
    }

    @Override
    public void removeNode(long id) {
        animalRoot.removeChild(id);
    }
}
