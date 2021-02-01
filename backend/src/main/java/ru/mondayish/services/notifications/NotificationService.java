package ru.mondayish.services.notifications;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mondayish.models.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    public static final List<SseEmitter> emitters = new ArrayList<>();

    public void sendNotification(Notification notification) {
        List<SseEmitter> emittersToRemove = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(notification, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emittersToRemove.add(emitter);
            }
        });
        emitters.removeAll(emittersToRemove);
    }
}
