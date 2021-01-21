package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.services.notifications.NotificationService;
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

    @GetMapping("/stream")
    public SseEmitter getStream() {
        SseEmitter sseEmitter = new SseEmitter();
        NotificationService.emitters.add(sseEmitter);
        return sseEmitter;
    }
}
