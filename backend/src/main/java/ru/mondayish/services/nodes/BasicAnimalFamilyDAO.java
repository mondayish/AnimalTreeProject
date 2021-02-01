package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.annotations.Emitted;
import ru.mondayish.models.node.NodeLevel;
import ru.mondayish.models.tree.AnimalFamily;
import ru.mondayish.models.node.Node;
import ru.mondayish.services.search.RootSearchService;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicAnimalFamilyDAO implements AnimalNodesDAO<AnimalFamily> {

    private final RootSearchService searchService;

    @Autowired
    public BasicAnimalFamilyDAO(RootSearchService searchService) {
        this.searchService = searchService;
    }

    @Emitted(nodeLevel = NodeLevel.FAMILY)
    @Override
    public AnimalFamily addNode(AnimalFamily animalFamily, Long parentId) throws IllegalArgumentException {
        animalFamily.setId(IdGenerator.getFamilyId());
        Node parent = searchService.findAnimalSquadById(parentId);
        parent.addChild(animalFamily);
        return animalFamily;
    }

    @Emitted(nodeLevel = NodeLevel.FAMILY, method = RequestMethod.PUT)
    @Override
    public void editNode(AnimalFamily animalFamily, Long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalSquadById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, animalFamily);

    }

    @Emitted(nodeLevel = NodeLevel.FAMILY, method = RequestMethod.DELETE)
    @Override
    public void removeNode(Long id, Long parentId) throws IllegalArgumentException {
        searchService.findAnimalSquadById(parentId).removeChild(id);
    }
}
