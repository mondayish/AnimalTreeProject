package ru.mondayish.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.mondayish.services.notifications.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor extends HandlerInterceptorAdapter {

    private final NotificationService notificationService;

    @Autowired
    public NotificationInterceptor(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("From interceptor, method = " + request.getMethod());
        if (!request.getMethod().equals("GET") && request.getServletPath().contains("tree")) {
            notificationService.sendNotification();
        }
    }
}
