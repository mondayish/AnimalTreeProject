package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.annotations.Emitted;
import ru.mondayish.models.node.Node;
import ru.mondayish.models.node.NodeLevel;
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

    @Emitted(nodeLevel = NodeLevel.CONCRETE_ANIMAL)
    @Override
    public ConcreteAnimal addNode(ConcreteAnimal concreteAnimal, Long parentId) throws IllegalArgumentException {
        concreteAnimal.setId(IdGenerator.getConcreteAnimalId());
        Node parent = searchService.findAnimalFamilyById(parentId);
        parent.addChild(concreteAnimal);
        return concreteAnimal;
    }

    @Emitted(nodeLevel = NodeLevel.CONCRETE_ANIMAL, method = RequestMethod.PUT)
    @Override
    public void editNode(ConcreteAnimal concreteAnimal, Long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalFamilyById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, concreteAnimal);
    }

    @Emitted(nodeLevel = NodeLevel.CONCRETE_ANIMAL, method = RequestMethod.DELETE)
    @Override
    public void removeNode(Long id, Long parentId) throws IllegalArgumentException {
        searchService.findAnimalFamilyById(parentId).removeChild(id);
    }
}
