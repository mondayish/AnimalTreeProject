package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalClass;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/type/{typeId}/class")
public class AnimalClassController {

    private final AnimalNodesDAO<AnimalClass> classService;

    @Autowired
    public AnimalClassController(AnimalNodesDAO<AnimalClass> classService) {
        this.classService = classService;
    }

    @PostMapping
    public ResponseEntity<AnimalClass> addAnimalClass(@PathVariable Long typeId, @RequestBody AnimalClass animalClass){
        try {
            return new ResponseEntity<>(classService.addNode(animalClass, typeId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalClass(@PathVariable Long typeId, @RequestBody AnimalClass animalClass){
        try {
            classService.editNode(animalClass, typeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<HttpStatus> removeAnimalClass(@PathVariable Long typeId, @PathVariable Long classId){
        try {
            classService.removeNode(classId, typeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
