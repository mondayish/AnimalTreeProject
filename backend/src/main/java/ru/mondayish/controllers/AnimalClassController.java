package ru.mondayish.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalClass;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/type/{parentId}/class")
public class AnimalClassController {

    private final AnimalNodesDAO<AnimalClass> classService;

    public AnimalClassController(AnimalNodesDAO<AnimalClass> classService) {
        this.classService = classService;
    }

    @PostMapping
    public ResponseEntity<AnimalClass> addAnimalClass(@PathVariable Long parentId, @RequestBody AnimalClass animalClass){
        try {
            return new ResponseEntity<>(classService.addNode(animalClass, parentId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalClass(@PathVariable Long parentId, @RequestBody AnimalClass animalClass){
        try {
            classService.editNode(animalClass, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{nodeId}")
    public ResponseEntity<HttpStatus> removeAnimalClass(@PathVariable Long parentId, @PathVariable Long nodeId){
        try {
            classService.removeNode(nodeId, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
