package main.dar.server;

import main.dar.server.annotation.Param;
import main.dar.server.annotation.WebHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iShavgula on 01/03/16.
 */
public class RouteBinding {
    private  String url;
    private HttpRequest.Method httpMethod;
    private ArrayList<Param> params;

    private Object handler;
    private Method method;

    public RouteBinding(Object handler, Method method, HttpRequest.Method httpMethod, String url) {
        this.handler = handler;
        this.method = method;

        this.httpMethod = httpMethod;
        this.url = url;
        params = new ArrayList<Param>();
    }

    public void addParam(Param httpParam) {
        params.add(httpParam);
    }

    public boolean match(HttpRequest request){
        if (!request.getMethod().equals(httpMethod)) {
            return false;
        }

        if (!request.getUrl().toLowerCase().equals(url.toLowerCase())) {
            return false;
        }

        for (Param p : params) {
            if (p.isOptional()) {
                continue;
            }
//            for (Param )
        }


        request.getParameters();

        return true;
    }

    public HttpResponse process(HttpRequest request){
        try {
            return (HttpResponse) method.invoke(handler,bindParams(request));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object[] bindParams(HttpRequest request) {
        return new Object[0];
    }


}