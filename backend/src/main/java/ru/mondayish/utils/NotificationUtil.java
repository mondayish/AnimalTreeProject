package ru.mondayish.utils;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mondayish.models.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationUtil {

    public static final List<SseEmitter> emitters = new ArrayList<>();

    public static void sendNotification(Notification notification) {
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
