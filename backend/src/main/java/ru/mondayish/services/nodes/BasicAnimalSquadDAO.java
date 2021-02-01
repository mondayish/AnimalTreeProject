package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.annotations.Emitted;
import ru.mondayish.models.node.Node;
import ru.mondayish.models.node.NodeLevel;
import ru.mondayish.models.tree.AnimalSquad;
import ru.mondayish.services.search.RootSearchService;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicAnimalSquadDAO implements AnimalNodesDAO<AnimalSquad> {

    private final RootSearchService searchService;

    @Autowired
    public BasicAnimalSquadDAO(RootSearchService searchService) {
        this.searchService = searchService;
    }

    @Emitted(nodeLevel = NodeLevel.SQUAD)
    @Override
    public AnimalSquad addNode(AnimalSquad animalSquad, Long parentId) throws IllegalArgumentException {
        animalSquad.setId(IdGenerator.getSquadId());
        Node parent = searchService.findAnimalClassById(parentId);
        parent.addChild(animalSquad);
        return animalSquad;
    }

    @Emitted(nodeLevel = NodeLevel.SQUAD, method = RequestMethod.PUT)
    @Override
    public void editNode(AnimalSquad animalSquad, Long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalClassById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, animalSquad);
    }

    @Emitted(nodeLevel = NodeLevel.SQUAD, method = RequestMethod.DELETE)
    @Override
    public void removeNode(Long id, Long parentId) throws IllegalArgumentException {
        searchService.findAnimalClassById(parentId).removeChild(id);
    }
}
