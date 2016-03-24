package main.dar.application;

import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iShavgula on 01/03/16.
 */
@WebHandler
public class PointMaster {

    HashMap<String, Point> points = new HashMap<>();
    Integer id = 0;

    @Route(method = HttpRequest.Method.GET, urlPattern = "/list")
    public HttpResponse ListPoints(HttpRequest request) {
        StringBuilder body = new StringBuilder();

        for (Map.Entry kvp : points.entrySet()) {
            Point p = (Point) kvp.getValue();

            body.append("point : ");
            body.append(kvp.getKey());

            body.append(" - ");

            body.append(p.x);
            body.append(",");
            body.append(p.y);

            body.append("\n");
        }

        HttpResponse res = new HttpResponse(body.toString(), 200);
        return res;
    }

    @Route(method = HttpRequest.Method.GET, urlPattern = "/p/$id/x")
    public HttpResponse GetPointX(HttpRequest request,
                                  @Param(value = "id", isUrlParam = true) String id) {
        if (points.containsKey(id)) {
            Point p = points.get(id);
            HttpResponse res = new HttpResponse(""+p.x, 200);
            return res;
        }
        return new HttpResponse("No point found", 404);
    }


    @Route(method = HttpRequest.Method.GET, urlPattern = "/p/$id/y")
    public HttpResponse GetPointY(HttpRequest request,
                                  @Param(value = "id", isUrlParam = true) String id) {
        if (points.containsKey(id)) {
            Point p = points.get(id);
            HttpResponse res = new HttpResponse(""+p.y, 200);

            return res;
        }
        return new HttpResponse("No point found", 404);
    }


    @Route(method = HttpRequest.Method.PUT, urlPattern = "/p/$id")
    public HttpResponse UdpatePoint(HttpRequest request,
            @Param(value = "id", isUrlParam = true) String id,
            @Param(value = "x", isOptional = true) Integer x,
            @Param(value = "y", isOptional = true) Integer y) {
        System.out.println("update for " + id + "with x : " + x + "y : " + y);
        if (points.get(id) == null) {
            return new HttpResponse("Id not found", 401);
        }
        Point p = points.get(id);
        if (x != null) {
            p.x = x;
        }
        if (y != null) {
            p.y = y;
        }
        points.put(id, p);
        return new HttpResponse("Update successful",200);
    }

    @Route(method = HttpRequest.Method.POST, urlPattern = "/p")
    public HttpResponse CreatePoint(HttpRequest request,
                                    @Param(value = "x") Integer x,
                                    @Param(value = "y") Integer y) {
        System.out.println("create with x : " + x + "y : " + y);

        String stringId = ""+id;
        id++;
        points.put(stringId,new Point(x,y));
        return new HttpResponse(stringId,200);
    }

    @Route(method = HttpRequest.Method.DELETE, urlPattern = "/p/$id")
    public HttpResponse DeletePoint(HttpRequest request,
                                    @Param(value = "id", isUrlParam = true) String id) {
        points.remove(id);
        return new HttpResponse("",200);
    }
}
