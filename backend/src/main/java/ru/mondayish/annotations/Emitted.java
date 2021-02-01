package ru.mondayish.annotations;

import org.springframework.web.bind.annotation.RequestMethod;
import ru.mondayish.models.node.NodeLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Emitted {
    RequestMethod method() default RequestMethod.POST;

    NodeLevel nodeLevel();
}
