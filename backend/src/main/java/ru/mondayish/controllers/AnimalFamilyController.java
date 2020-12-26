package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalFamily;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/squad/{squadId}/family")
public class AnimalFamilyController {

    private final AnimalNodesDAO<AnimalFamily> familyService;

    @Autowired
    public AnimalFamilyController(AnimalNodesDAO<AnimalFamily> familyService) {
        this.familyService = familyService;
    }

    @PostMapping
    public ResponseEntity<AnimalFamily> addAnimalFamily(@PathVariable Long squadId, @RequestBody AnimalFamily animalFamily){
        try {
            return new ResponseEntity<>(familyService.addNode(animalFamily, squadId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalFamily(@PathVariable Long squadId, @RequestBody AnimalFamily animalFamily){
        try {
            familyService.editNode(animalFamily, squadId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{familyId}")
    public ResponseEntity<HttpStatus> removeAnimalFamily(@PathVariable Long squadId, @PathVariable Long familyId){
        try {
            familyService.removeNode(familyId, squadId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
