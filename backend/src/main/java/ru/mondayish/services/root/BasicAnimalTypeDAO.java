package ru.mondayish.services.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.models.tree.AnimalType;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicAnimalTypeDAO implements AnimalRootDAO<AnimalType> {

    private final AnimalRoot animalRoot;

    @Autowired
    public BasicAnimalTypeDAO(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    @Override
    public AnimalType addNode(AnimalType animalType) {
        animalType.setId(IdGenerator.getTypeId());
        animalRoot.addChild(animalType);
        return animalType;
    }

    @Override
    public boolean editNode(AnimalType node) {
        boolean isCorrectId = animalRoot
                .getChildren()
                .stream()
                .anyMatch(child -> child.getId() == node.getId());
        if (isCorrectId) {
            animalRoot.removeChild(node.getId());
            animalRoot.addChild(node);
        }
        return isCorrectId;
    }

    @Override
    public void removeNode(long id) {
        animalRoot.removeChild(id);
    }
}
