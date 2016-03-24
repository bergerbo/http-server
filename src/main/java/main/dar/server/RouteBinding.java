package main.dar.server;

import main.dar.server.Types.ParamType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iShavgula on 01/03/16.
 */
public class RouteBinding {
    private  String urlPattern;
    private HttpRequest.Method httpMethod;
    private ArrayList<ParamType> params;

    private Object handler;
    private Method method;

    public RouteBinding(Object handler, Method method, HttpRequest.Method httpMethod, String urlPattern) {
        this.handler = handler;
        this.method = method;

        this.httpMethod = httpMethod;
        this.urlPattern = urlPattern;
        params = new ArrayList<ParamType>();
    }

    public void addParam(ParamType httpParam) {
        params.add(httpParam);
    }

    public boolean match(HttpRequest request){
        if (!request.getMethod().equals(httpMethod)) {
            return false;
        }

        if (!canMatchUrl(urlPattern, request)) {
            return false;
        }

//        if (!request.getUrl().toLowerCase().matches(urlPattern.toLowerCase())) {
//            return false;
//        }

        HashMap<String, String> requestParameters = request.getParameters();

        for (ParamType p : params) {
            if (p.getParam().isUrlParam()) continue;
            if (requestParameters.get(p.getParam().value()) == null && !p.getParam().isOptional()) {
                return false;
            }

//            String name = p.getParam().value();
            String value = requestParameters.get(p.getParam().value());
//            Class<?> type = p.getType();

            if (!canParseValueWithParamType(value, p)) {
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
        Object [] result = new Object[params.size()];
        for (int i = 0; i < params.size(); i ++) {
            HashMap<String, String> requestParameters = request.getParameters();
            requestParameters.putAll(request.getUrlParameters());
            String val = request.getParameters().get(params.get(i).getParam().value());
            if (val == null || params.get(i).isString()) {
                result[i] = val;
                continue;
            }

            if (params.get(i).isInteger()) {
                result[i] = Integer.parseInt(val);
                continue;
            }

            if (params.get(i).isDouble()) {
                result[i] = Double.parseDouble(val);
                continue;
            }

            if (params.get(i).isFloat()) {
                result[i] = Float.parseFloat(val);
                continue;
            }

            if (params.get(i).isBoolean()) {
                result[i] = Boolean.parseBoolean(val);
                continue;
            }

            return null;
        }

        return result;
    }

    private boolean canMatchUrl(String urlPattern, HttpRequest request) {
        request.setUrlParameters(new HashMap<>());

        String[] urlPatternSegments = urlPattern.split("/");
        String[] requestUrlSegments = request.getUrl().split("/");

        if (urlPatternSegments.length != requestUrlSegments.length) {
            return false;
        }

        for (int i = 0; i < urlPatternSegments.length; i ++) {
            if (urlPatternSegments[i].toLowerCase().equals(requestUrlSegments[i].toLowerCase())) {
                continue;
            } else if (!urlPatternSegments[i].startsWith("$")) {
                return false;
            }

            String paramName = urlPatternSegments[i].substring(1).toLowerCase();

            for (ParamType p : params) {
                if (!p.getParam().isUrlParam()) continue;
                if (p.getParam().value().toLowerCase().equals(paramName)) {
                    if (!canParseValueWithParamType(requestUrlSegments[i], p)) {
                        return false;
                    } else {
                        request.getUrlParameters().put(paramName, requestUrlSegments[i]);
                    }
                }
            }
        }

        return true;
    }

    private boolean canParseValueWithParamType(String value, ParamType type) {
        if (value == null && type.getParam().isOptional()) {
            return true;
        } else {
            if (value == null) {
                return false;
            }
        }

        if (type.isString()) {
            return true;
        }

        if (type.isInteger()) {
            try {
                int _ = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.isDouble()) {
            try {
                double _ = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.isFloat()) {
            try {
                Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        if (type.isBoolean()) {
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
