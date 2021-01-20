package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.node.Node;
import ru.mondayish.models.tree.ConcreteAnimal;
import ru.mondayish.services.search.RootSearchService;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicConcreteAnimalDAO implements AnimalNodesDAO<ConcreteAnimal> {

    private final RootSearchService searchService;

    @Autowired
    public BasicConcreteAnimalDAO(RootSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public ConcreteAnimal addNode(ConcreteAnimal concreteAnimal, long parentId) throws IllegalArgumentException {
        concreteAnimal.setId(IdGenerator.getConcreteAnimalId());
        Node parent = searchService.findAnimalFamilyById(parentId);
        parent.addChild(concreteAnimal);
        return concreteAnimal;
    }

    @Override
    public void editNode(ConcreteAnimal concreteAnimal, long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalFamilyById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, concreteAnimal);
    }

    @Override
    public void removeNode(long id, long parentId) throws IllegalArgumentException {
        searchService.findAnimalFamilyById(parentId).removeChild(id);
    }
}
