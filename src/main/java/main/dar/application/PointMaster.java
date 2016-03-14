package main.dar.application;

import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

/**
 * Created by iShavgula on 01/03/16.
 */
@WebHandler
public class PointMaster {

    @Route(method = HttpRequest.Method.GET, urlPattern = "^/list$")
    public HttpResponse ListPoints() {
        System.out.println("return list");
        return null;
    }

    @Route(method = HttpRequest.Method.GET, urlPattern = "^/p/d+/x$")
    public HttpResponse GetPointX(@Param("id") String id) {
        System.out.println("get x for " + id);
        return null;
    }


    @Route(method = HttpRequest.Method.GET, urlPattern = "^/p/d+/y$")
    public HttpResponse GetPointY(@Param("id") String id) {
        System.out.println("get y for " + id);
        return null;
    }


    @Route(method = HttpRequest.Method.PUT, urlPattern = "^/p/w$")
    public HttpResponse UdpatePoint(
                                    @Param(value = "x", isOptional = true) Integer x,
                                    @Param("id") String id,
                                    @Param(value = "y", isOptional = true) Integer y) {
        System.out.println("update for " + id + "with x : "+ x + "y : " + y);
        return null;
    }

    @Route(method = HttpRequest.Method.POST, urlPattern = "^/p$")
    public HttpResponse CreatePoint(@Param(value = "x") Integer x,
                                    @Param(value = "y") Integer y) {
        System.out.println("create with x : "+ x + "y : " + y);
        return null;
    }

    @Route(method = HttpRequest.Method.DELETE, urlPattern = "^/p/w+$")
    public HttpResponse DeletePoint(@Param("id") String id) {
        System.out.println("get y for " + id);
        return null;
    }
}
