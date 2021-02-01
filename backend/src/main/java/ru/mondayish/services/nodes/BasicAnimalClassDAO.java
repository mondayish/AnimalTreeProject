package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.bpp.Emitted;
import ru.mondayish.bpp.NodeLevel;
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

    @Emitted(nodeLevel = NodeLevel.CLASS)
    @Override
    public AnimalClass addNode(AnimalClass animalClass, Long parentId) throws IllegalArgumentException {
        animalClass.setId(IdGenerator.getClassId());
        Node parent = searchService.findAnimalTypeById(parentId);
        parent.addChild(animalClass);
        return animalClass;
    }

    @Emitted(nodeLevel = NodeLevel.CLASS, method = RequestMethod.PUT)
    @Override
    public void editNode(AnimalClass animalClass, Long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalTypeById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, animalClass);
    }

    @Emitted(nodeLevel = NodeLevel.CLASS, method = RequestMethod.DELETE)
    @Override
    public void removeNode(Long id, Long parentId) throws IllegalArgumentException {
        searchService.findAnimalTypeById(parentId).removeChild(id);
    }
}
