package main.dar.server.annotation;

import main.dar.server.HttpRequest;

/**
 * Created by iShavgula on 23/02/16.
 */
public @interface Route {
    HttpRequest.Method method();
    String url();
}