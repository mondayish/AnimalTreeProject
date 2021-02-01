package ru.mondayish.bpp;

import lombok.Data;

@Data
public class Notification {

    private final String requestMethod;
    private final Object result;
    private final long parentId;
    private final int level;
    private final long deletedId;

    private Notification(String requestMethod, Object result, long parentId, int level, long deletedId) {
        this.requestMethod = requestMethod;
        this.result = result;
        this.parentId = parentId;
        this.level = level;
        this.deletedId = deletedId;
    }

    public static Notification addNotification(Object result, long parentId, int level) {
        return new Notification("POST", result, parentId, level, 0);
    }

    public static Notification editNotification(Object result, long parentId, int level) {
        return new Notification("PUT", result, parentId, level, 0);
    }

    public static Notification deleteNotification(long deletedId, int level) {
        return new Notification("DELETE", null, 0, level, deletedId);
    }
}
