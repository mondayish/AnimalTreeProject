package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.tree.AnimalClass;
import ru.mondayish.models.node.Node;
import ru.mondayish.services.search.RootSearchService;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicAnimalClassDAO implements AnimalNodesDAO<AnimalClass> {

    private final RootSearchService searchService;

    @Autowired
    public BasicAnimalClassDAO(RootSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public AnimalClass addNode(AnimalClass animalClass, long parentId) throws IllegalArgumentException {
        animalClass.setId(IdGenerator.getClassId());
        Node parent = searchService.findAnimalTypeById(parentId);
        parent.addChild(animalClass);
        return animalClass;
    }

    @Override
    public void editNode(AnimalClass animalClass, long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalTypeById(parentId);
        boolean isCorrectId = parent
                .getChildren()
                .stream()
                .anyMatch(child -> child.getId() == animalClass.getId());
        if (isCorrectId) {
            parent.removeChild(animalClass.getId());
            parent.addChild(animalClass);
        }
    }

    @Override
    public void removeNode(long id, long parentId) throws IllegalArgumentException {
        searchService.findAnimalTypeById(parentId).removeChild(id);
    }
}
