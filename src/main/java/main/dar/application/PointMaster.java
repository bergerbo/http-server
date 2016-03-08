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
    public HttpResponse ListPoints(@Param("chocolat") String chocolat) {
        System.out.println("return list");
        return null;
    }
}
