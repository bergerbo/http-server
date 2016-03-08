package main.dar.server;

import main.dar.server.annotation.Param;
import main.dar.server.annotation.Types.ParamType;
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
    private ArrayList<ParamType> params;

    private Object handler;
    private Method method;

    public RouteBinding(Object handler, Method method, HttpRequest.Method httpMethod, String url) {
        this.handler = handler;
        this.method = method;

        this.httpMethod = httpMethod;
        this.url = url;
        params = new ArrayList<ParamType>();
    }

    public void addParam(ParamType httpParam) {
        params.add(httpParam);
    }

    public boolean match(HttpRequest request){
        if (!request.getMethod().equals(httpMethod)) {
            return false;
        }

        if (!request.getUrl().toLowerCase().equals(url.toLowerCase())) {
            return false;
        }

        HashMap<String, String> requestParameters = request.getParameters();

        for (ParamType p : params) {
            if (requestParameters.get(p.getParam().value()) == null && !p.getParam().isOptional()) {
                return false;
            }

            String name = p.getParam().value();
            String value = requestParameters.get(p.getParam().value());
            Class<?> type = p.getType();

            if (!canParseValueWithType(value, type)) {
                return false;
            }
        }

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

    private boolean canParseValueWithType(String value, Class<?> type) {
        if (type.toString().equals(String.class.toString())) {
            return true;
        }

        if (type.toString().equals(Integer.class.toString()) ||
                type.toString().equals(int.class.toString())) {
            try {
                int _ = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.toString().equals(Double.class.toString()) ||
                type.toString().equals(double.class.toString())) {
            try {
                double _ = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.toString().equals(Float.class.toString()) ||
                type.toString().equals(float.class.toString())) {
            try {
                Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.toString().equals(Boolean.class.toString()) ||
                type.toString().equals(boolean.class.toString())) {
            try {
                Boolean.parseBoolean(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        return false;
    }


}
