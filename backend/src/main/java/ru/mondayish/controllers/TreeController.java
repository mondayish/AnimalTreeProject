package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.models.tree.AnimalType;

@RestController
@RequestMapping("/tree/root")
public class TreeController {

    private final AnimalRoot root;

    @Autowired
    public TreeController(AnimalRoot root) {
        this.root = root;
    }

    @PostMapping("/type")
    public void addAnimalType(@RequestBody AnimalType animalType){
        root.addChild(animalType);
    }

    @GetMapping
    public AnimalRoot getRoot(){
        return root;
    }

    @DeleteMapping("/type/{id}")
    public void removeAnimalType(){

    }
}
