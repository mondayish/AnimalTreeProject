package ru.mondayish.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.annotations.Emitted;
import ru.mondayish.models.Notification;
import ru.mondayish.models.node.Node;
import ru.mondayish.models.node.NodeLevel;
import ru.mondayish.utils.NotificationUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class EmittedHandlerAspect {

    @AfterReturning(value = "@annotation(ru.mondayish.annotations.Emitted)", returning = "returnValue")
    public void sendNotificationAfterAction(JoinPoint joinPoint, Object returnValue) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Emitted emitted = method.getAnnotation(Emitted.class);
        RequestMethod requestMethod = emitted.method();
        switch (requestMethod) {
            case POST:
                sendPostNotification(joinPoint.getArgs(), emitted.nodeLevel(), returnValue);
                break;
            case PUT:
                sendPutNotification(joinPoint.getArgs(), emitted.nodeLevel());
                break;
            case DELETE:
                sendDeleteNotification(joinPoint.getArgs(), emitted.nodeLevel());
                break;
            default:
                throw new IllegalArgumentException("This method is not supported now");
        }
    }

    private void sendDeleteNotification(Object[] args, NodeLevel nodeLevel) {
        List<Long> ids = Arrays.stream(args).filter(o -> o.getClass().equals(Long.class))
                .map(Long.class::cast).collect(Collectors.toList());
        NotificationUtil.sendNotification(Notification.deleteNotification(ids.get(0), nodeLevel.getLevel()));
    }

    private void sendPutNotification(Object[] args, NodeLevel nodeLevel) {
        long parentId = Arrays.stream(args).filter(o -> o.getClass().equals(Long.class)).mapToLong(Long.class::cast).findAny().orElse(0L);
        Object result = Arrays.stream(args)
                .filter(o -> Arrays.asList(o.getClass().getInterfaces()).contains(Node.class) || Arrays.asList(o.getClass().getSuperclass().getInterfaces()).contains(Node.class))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Nothing to put in emitting aspect"));
        NotificationUtil.sendNotification(Notification.editNotification(result, parentId, nodeLevel.getLevel()));
    }

    private void sendPostNotification(Object[] args, NodeLevel nodeLevel, Object result) {
        Long parentId = (Long) Arrays.stream(args).filter(o -> o.getClass().equals(Long.class)).findAny().orElse(0L);
        NotificationUtil.sendNotification(Notification.addNotification(result, parentId, nodeLevel.getLevel()));
    }
}
