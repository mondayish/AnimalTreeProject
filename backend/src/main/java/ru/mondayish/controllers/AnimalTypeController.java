package ru.mondayish.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalType;
import ru.mondayish.services.root.AnimalRootDAO;

@RestController
@RequestMapping("/tree/type")
public class AnimalTypeController {

    private final AnimalRootDAO<AnimalType> typeService;

    public AnimalTypeController(AnimalRootDAO<AnimalType> typeService) {
        this.typeService = typeService;
    }

    @PostMapping
    public AnimalType addAnimalType(@RequestBody AnimalType animalType) {
        return typeService.addNode(animalType);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalType(@RequestBody AnimalType animalType) {
        return typeService.editNode(animalType) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{nodeId}")
    public ResponseEntity<HttpStatus> removeAnimalType(@PathVariable Long nodeId) {
        typeService.removeNode(nodeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
