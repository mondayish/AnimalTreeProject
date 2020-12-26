package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mondayish.models.tree.AnimalRoot;

@RestController
@RequestMapping("/tree/root")
public class TreeRootController {

    private final AnimalRoot root;

    @Autowired
    public TreeRootController(AnimalRoot root) {
        this.root = root;
    }

    @GetMapping
    public AnimalRoot getRoot() {
        return root;
    }
}
