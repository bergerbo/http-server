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

    @Route(method = HttpRequest.Method.GET, url = "/list")
    public HttpResponse ListPoints() {
        System.out.println("return list");
        return null;
    }

    @Route(method = HttpRequest.Method.GET, url = "/p/<id>/x")
    public HttpResponse GetPointX(@Param("id") String id) {
        System.out.println("get x for " + id);
        return null;
    }


    @Route(method = HttpRequest.Method.GET, url = "/p/<id>/y")
    public HttpResponse GetPointY(@Param("id") String id) {
        System.out.println("get y for " + id);
        return null;
    }


    @Route(method = HttpRequest.Method.PUT, url = "/p/<id>")
    public HttpResponse UdpatePoint(@Param("id") String id,
                                    @Param(value = "x", isOptional = true) Integer x,
                                    @Param(value = "y", isOptional = true) Integer y) {
        System.out.println("update for " + id + "with x : "+ x + "y : " + y);
        return null;
    }

    @Route(method = HttpRequest.Method.POST, url = "/p")
    public HttpResponse CreatePoint(@Param(value = "x") Integer x,
                                    @Param(value = "y") Integer y) {
        System.out.println("create with x : "+ x + "y : " + y);
        return null;
    }

    @Route(method = HttpRequest.Method.DELETE, url = "/p/<id>")
    public HttpResponse DeletePoint(@Param("id") String id) {
        System.out.println("get y for " + id);
        return null;
    }
}
