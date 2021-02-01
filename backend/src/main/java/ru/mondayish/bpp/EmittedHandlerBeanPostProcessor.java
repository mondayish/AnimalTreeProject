package ru.mondayish.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.services.notifications.NotificationService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EmittedHandlerBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> originalClasses = new HashMap<>();
    private final NotificationService notificationService;

    public EmittedHandlerBeanPostProcessor(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Method> methodsToEmit = getMethodsToEmit(bean.getClass());
        if (!methodsToEmit.isEmpty()) {
            originalClasses.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> originalClass = originalClasses.get(beanName);
        if (originalClass != null) {
            List<Method> originalMethodsToEmit = getMethodsToEmit(originalClass);
            return Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(), (proxy, method, args) -> {
                Optional<Method> originalMethodOpt = originalMethodsToEmit
                        .stream()
                        .filter(m -> m.getName().equals(method.getName()) && Arrays.equals(m.getTypeParameters(), method.getTypeParameters()))
                        .findAny();
                if (originalMethodOpt.isPresent()) {
                    Emitted annotation = originalMethodOpt.get().getAnnotation(Emitted.class);
                    RequestMethod requestMethod = annotation.method();
                    NodeLevel nodeLevel = annotation.nodeLevel();
                    Object retValue = method.invoke(bean, args);
                    switch (requestMethod) {
                        case POST:
                            long parentId = nodeLevel == NodeLevel.TYPE ? 0 : (Long) findArgumentByClass(Long.class, args);
                            notificationService.sendNotification(Notification.addNotification(retValue, parentId, nodeLevel.getLevel()));
                            break;
                        case PUT:
                            parentId = nodeLevel == NodeLevel.TYPE ? 0 : (Long) findArgumentByClass(Long.class, args);
                            Object result = findArgumentByClass(nodeLevel.getNodeClass(), args);
                            notificationService.sendNotification(Notification.editNotification(result, parentId, nodeLevel.getLevel()));
                            break;
                        case DELETE:
                            Long idToDelete = (Long) findIdToDelete(args);
                            notificationService.sendNotification(Notification.deleteNotification(idToDelete, nodeLevel.getLevel()));
                            break;
                        default:
                            throw new IllegalArgumentException("This method is not supported now");
                    }
                    return retValue;
                }
                return method.invoke(bean, args);
            });
        }
        return bean;
    }

    private List<Method> getMethodsToEmit(Class<?> classForSearch) {
        return Arrays
                .stream(classForSearch.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Emitted.class))
                .collect(Collectors.toList());
    }

    private Object findArgumentByClass(Class<?> classToFind, Object... args) {
        return Arrays.stream(args).filter(arg -> arg.getClass().equals(classToFind))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Something strange in Spring..."));
    }

    private Object findIdToDelete(Object... args) {
        return args[0];
    }
}
