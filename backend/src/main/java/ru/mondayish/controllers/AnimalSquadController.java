package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalSquad;
import ru.mondayish.services.nodes.AnimalNodesDAO;

@RestController
@RequestMapping("/tree/class/{classId}/squad")
public class AnimalSquadController {

    private final AnimalNodesDAO<AnimalSquad> squadService;

    @Autowired
    public AnimalSquadController(AnimalNodesDAO<AnimalSquad> squadService) {
        this.squadService = squadService;
    }

    @PostMapping
    public ResponseEntity<AnimalSquad> addAnimalSquad(@PathVariable Long classId, @RequestBody AnimalSquad animalSquad){
        try {
            return new ResponseEntity<>(squadService.addNode(animalSquad, classId), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editAnimalSquad(@PathVariable Long classId, @RequestBody AnimalSquad animalSquad){
        try {
            squadService.editNode(animalSquad, classId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{squadId}")
    public ResponseEntity<HttpStatus> removeAnimalSquad(@PathVariable Long classId, @PathVariable Long squadId){
        try {
            squadService.removeNode(squadId, classId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
