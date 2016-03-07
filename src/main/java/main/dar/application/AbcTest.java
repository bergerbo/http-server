package main.dar.application;

import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

/**
 * Created by iShavgula on 01/03/16.
 */
@WebHandler
public class AbcTest {

    @Route(method = HttpRequest.Method.GET, url = "/asd")
    HttpResponse getAll(@Param("chocolat") String chocolat, @Param("C2") String c2, @Param("C3") String c3) {
        return null;
    }
}
