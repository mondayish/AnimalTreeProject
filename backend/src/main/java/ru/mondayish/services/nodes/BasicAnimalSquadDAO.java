package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.node.Node;
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

    @Override
    public AnimalSquad addNode(AnimalSquad animalSquad, long parentId) throws IllegalArgumentException {
        animalSquad.setId(IdGenerator.getSquadId());
        Node parent = searchService.findAnimalClassById(parentId);
        parent.addChild(animalSquad);
        return animalSquad;
    }

    @Override
    public void editNode(AnimalSquad animalSquad, long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalClassById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, animalSquad);
    }

    @Override
    public void removeNode(long id, long parentId) throws IllegalArgumentException {
        searchService.findAnimalClassById(parentId).removeChild(id);
    }
}
