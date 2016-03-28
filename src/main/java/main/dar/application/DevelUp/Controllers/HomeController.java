package main.dar.application.DevelUp.Controllers;

import main.dar.application.DevelUp.Model.DB;
import main.dar.application.DevelUp.Model.User;
import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by iShavgula on 28/03/2016.
 */

@WebHandler
public class HomeController {



    @Route(method = HttpRequest.Method.GET, urlPattern = "/home")
    public HttpResponse home(HttpRequest request){
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        try {
            String body = TemplateProcessor.process("index.html", DB.getInstance().getAllUsersData().build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }
}
