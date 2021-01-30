package ru.mondayish.services.notifications;

import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mondayish.services.root.AnimalRootService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    public static final List<SseEmitter> emitters = new ArrayList<>();
    private final AnimalRootService service;

    public NotificationService(AnimalRootService service) {
        this.service = service;
    }

    public void sendNotification() {
        List<SseEmitter> emittersToRemove = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(service.getAnimalRoot(), MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emittersToRemove.add(emitter);
            }
        });
        emitters.removeAll(emittersToRemove);
    }
}
