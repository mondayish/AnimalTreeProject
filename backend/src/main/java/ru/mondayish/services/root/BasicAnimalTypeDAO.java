package ru.mondayish.services.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.bpp.Emitted;
import ru.mondayish.bpp.NodeLevel;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.models.tree.AnimalType;
import ru.mondayish.services.nodes.NodeHelpService;
import ru.mondayish.utils.IdGenerator;

@Service
public class BasicAnimalTypeDAO implements AnimalRootDAO<AnimalType> {

    private final AnimalRoot animalRoot;

    @Autowired
    public BasicAnimalTypeDAO(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    @Emitted(nodeLevel = NodeLevel.TYPE)
    @Override
    public AnimalType addNode(AnimalType animalType) {
        animalType.setId(IdGenerator.getTypeId());
        animalRoot.addChild(animalType);
        return animalType;
    }

    @Emitted(nodeLevel = NodeLevel.TYPE, method = RequestMethod.PUT)
    @Override
    public boolean editNode(AnimalType node) {
        return NodeHelpService.editNodeWithoutChildren(animalRoot, node);
    }

    @Emitted(nodeLevel = NodeLevel.TYPE, method = RequestMethod.DELETE)
    @Override
    public void removeNode(Long id) {
        animalRoot.removeChild(id);
    }
}
