package ru.mondayish.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalSquad;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/class/{parentId}/squad")
public class AnimalSquadController {

    private final AnimalNodesDAO<AnimalSquad> squadService;

    public AnimalSquadController(AnimalNodesDAO<AnimalSquad> squadService) {
        this.squadService = squadService;
    }

    @PostMapping
    public ResponseEntity<AnimalSquad> addAnimalSquad(@PathVariable Long parentId, @RequestBody AnimalSquad animalSquad){
        try {
            return new ResponseEntity<>(squadService.addNode(animalSquad, parentId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalSquad(@PathVariable Long parentId, @RequestBody AnimalSquad animalSquad){
        try {
            squadService.editNode(animalSquad, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{nodeId}")
    public ResponseEntity<HttpStatus> removeAnimalSquad(@PathVariable Long parentId, @PathVariable Long nodeId){
        try {
            squadService.removeNode(nodeId, parentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
