package main.dar.server;

import main.dar.server.annotation.Param;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by iShavgula on 01/03/16.
 */
public class RouteBinding {
    private  String url;
    private HttpRequest.Method httpMethod;
    private Param[] params;
//    private HashMap

    private Method method;

    void bindUrl(String url) {
        this.url = url;
    }

    void bindHttpMethod(HttpRequest.Method method) {
        this.httpMethod = method;
    }

    // TODO: add params' setter

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
        return null;
    }


}
