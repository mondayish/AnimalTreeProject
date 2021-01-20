package ru.mondayish.services.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.tree.AnimalFamily;
import ru.mondayish.models.node.Node;
import ru.mondayish.services.search.RootSearchService;
import ru.mondayish.utils.IdGenerator;

import java.util.Optional;

@Service
public class BasicAnimalFamilyDAO implements AnimalNodesDAO<AnimalFamily> {

    private final RootSearchService searchService;

    @Autowired
    public BasicAnimalFamilyDAO(RootSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public AnimalFamily addNode(AnimalFamily animalFamily, long parentId) throws IllegalArgumentException {
        animalFamily.setId(IdGenerator.getFamilyId());
        Node parent = searchService.findAnimalSquadById(parentId);
        parent.addChild(animalFamily);
        return animalFamily;
    }

    @Override
    public void editNode(AnimalFamily animalFamily, long parentId) throws IllegalArgumentException {
        Node parent = searchService.findAnimalSquadById(parentId);
        NodeHelpService.editNodeWithoutChildren(parent, animalFamily);

    }

    @Override
    public void removeNode(long id, long parentId) throws IllegalArgumentException {
        searchService.findAnimalSquadById(parentId).removeChild(id);
    }
}
