package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.services.root.AnimalRootService;

@RestController
@RequestMapping("/tree/root")
public class TreeRootController {

    private final AnimalRootService service;

    @Autowired
    public TreeRootController(AnimalRootService service) {
        this.service = service;
    }

    @GetMapping
    public AnimalRoot getRoot() {
        return service.getAnimalRoot();
    }
}
