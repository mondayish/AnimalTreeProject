package ru.mondayish.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalFamily;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/squad/{parentId}/family")
public class AnimalFamilyController {

    private final AnimalNodesDAO<AnimalFamily> familyService;

    public AnimalFamilyController(AnimalNodesDAO<AnimalFamily> familyService) {
        this.familyService = familyService;
    }

    @PostMapping
    public ResponseEntity<AnimalFamily> addAnimalFamily(@PathVariable Long parentId, @RequestBody AnimalFamily animalFamily){
        try {
            return new ResponseEntity<>(familyService.addNode(animalFamily, parentId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalFamily(@PathVariable Long parentId, @RequestBody AnimalFamily animalFamily){
        try {
            familyService.editNode(animalFamily, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{nodeId}")
    public ResponseEntity<HttpStatus> removeAnimalFamily(@PathVariable Long parentId, @PathVariable Long nodeId){
        try {
            familyService.removeNode(nodeId, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
