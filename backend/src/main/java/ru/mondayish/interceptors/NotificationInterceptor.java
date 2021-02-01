//package ru.mondayish.interceptors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import ru.mondayish.services.notifications.NotificationService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class NotificationInterceptor extends HandlerInterceptorAdapter {
//
//    private final static List<String> METHODS_TO_INTERCEPT = Arrays.asList("POST", "PUT", "DELETE");
//    private final NotificationService notificationService;
//
//    @Autowired
//    public NotificationInterceptor(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        System.out.println("From interceptor, method = " + request.getMethod());
//        if (METHODS_TO_INTERCEPT.contains(request.getMethod().toUpperCase()) && request.getServletPath().contains("tree")) {
//            request.getAttribute("animalType");
//            notificationService.sendNotification();
//        }
//    }
//}
