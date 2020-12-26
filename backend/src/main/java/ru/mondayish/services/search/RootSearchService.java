package ru.mondayish.services.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.models.node.Node;

import java.util.ArrayList;
import java.util.List;

@Service
public class RootSearchService {

    private final AnimalRoot animalRoot;

    @Autowired
    public RootSearchService(AnimalRoot animalRoot) {
        this.animalRoot = animalRoot;
    }

    public Node findAnimalTypeById(long id) throws IllegalArgumentException {
        return findNodeByIdInList(animalRoot.getChildren(), id);
    }

    public Node findAnimalClassById(long id) throws IllegalArgumentException {
        return findNodeByIdInList(getAllAnimalClasses(), id);
    }

    public Node findAnimalSquadById(long id) throws IllegalArgumentException {
        return findNodeByIdInList(getAllAnimalSquads(), id);
    }

    public Node findAnimalFamilyById(long id) throws IllegalArgumentException {
        return findNodeByIdInList(getAllAnimalFamilies(), id);
    }

    public Node findConcreteAnimalById(long id) throws IllegalArgumentException {
        return findNodeByIdInList(getAllConcreteAnimals(), id);
    }

    private Node findNodeByIdInList(List<Node> nodes, long id) throws IllegalArgumentException {
        return nodes
                .stream()
                .filter(child -> child.getId() == id)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<Node> getAllAnimalClasses() {
        return getInnerList(animalRoot.getChildren());
    }

    private List<Node> getAllAnimalSquads() {
        return getInnerList(getAllAnimalClasses());
    }

    private List<Node> getAllAnimalFamilies() {
        return getInnerList(getAllAnimalSquads());
    }

    private List<Node> getAllConcreteAnimals() {
        return getInnerList(getAllAnimalFamilies());
    }

    private List<Node> getInnerList(List<Node> externalList) {
        return externalList.stream().map(Node::getChildren).reduce((list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }).orElseGet(ArrayList::new);
    }
}
