package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.ConcreteAnimal;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/family/{familyId}/animal")
public class ConcreteAnimalController {

    private final AnimalNodesDAO<ConcreteAnimal> concreteAnimalService;

    @Autowired
    public ConcreteAnimalController(AnimalNodesDAO<ConcreteAnimal> concreteAnimalService) {
        this.concreteAnimalService = concreteAnimalService;
    }

    @PostMapping
    public ResponseEntity<ConcreteAnimal> addConcreteAnimal(@PathVariable Long familyId, @RequestBody ConcreteAnimal concreteAnimal) {
        try {
            return new ResponseEntity<>(concreteAnimalService.addNode(concreteAnimal, familyId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editConcreteAnimal(@PathVariable Long familyId, @RequestBody ConcreteAnimal concreteAnimal) {
        try {
            concreteAnimalService.editNode(concreteAnimal, familyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<HttpStatus> removeConcreteAnimal(@PathVariable Long familyId, @PathVariable Long animalId) {
        try {
            concreteAnimalService.removeNode(animalId, familyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
