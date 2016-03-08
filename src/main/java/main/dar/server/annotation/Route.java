package main.dar.server.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;
import main.dar.server.HttpRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by iShavgula on 23/02/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    HttpRequest.Method method();
    String url();
}