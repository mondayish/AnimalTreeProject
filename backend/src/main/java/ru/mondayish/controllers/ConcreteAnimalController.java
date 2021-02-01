package ru.mondayish.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.ConcreteAnimal;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/family/{parentId}/animal")
public class ConcreteAnimalController {

    private final AnimalNodesDAO<ConcreteAnimal> concreteAnimalService;

    public ConcreteAnimalController(AnimalNodesDAO<ConcreteAnimal> concreteAnimalService) {
        this.concreteAnimalService = concreteAnimalService;
    }

    @PostMapping
    public ResponseEntity<ConcreteAnimal> addConcreteAnimal(@PathVariable Long parentId, @RequestBody ConcreteAnimal concreteAnimal) {
        try {
            return new ResponseEntity<>(concreteAnimalService.addNode(concreteAnimal, parentId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editConcreteAnimal(@PathVariable Long parentId, @RequestBody ConcreteAnimal concreteAnimal) {
        try {
            concreteAnimalService.editNode(concreteAnimal, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{nodeId}")
    public ResponseEntity<HttpStatus> removeConcreteAnimal(@PathVariable Long parentId, @PathVariable Long nodeId) {
        try {
            concreteAnimalService.removeNode(nodeId, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
