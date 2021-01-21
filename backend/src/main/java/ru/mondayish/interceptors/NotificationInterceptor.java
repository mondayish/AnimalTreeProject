package ru.mondayish.interceptors;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.mondayish.services.notifications.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotificationInterceptor extends HandlerInterceptorAdapter {

    private final NotificationService notificationService;

    public NotificationInterceptor(NotificationService notificationService) {
        super();
        this.notificationService = notificationService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("From interceptor, method = "+request.getMethod());
        if(!request.getMethod().equals("GET") && request.getContextPath().contains("tree")){
            notificationService.sendNotification();
        }
    }
}
