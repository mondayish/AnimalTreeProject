package ru.mondayish.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mondayish.models.tree.AnimalRoot;
import ru.mondayish.utils.NotificationUtil;
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
        SseEmitter sseEmitter = new SseEmitter(-1L);
        NotificationUtil.emitters.add(sseEmitter);
        sseEmitter.onCompletion(() -> NotificationUtil.emitters.remove(sseEmitter));
        return sseEmitter;
    }
}
