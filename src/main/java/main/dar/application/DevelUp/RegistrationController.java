package main.dar.application.DevelUp;

import main.dar.server.HttpRequest;
import main.dar.server.HttpResponse;
import main.dar.server.TemplateProcessor;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import java.io.IOException;

/**
 * Created by hoboris on 3/28/16.
 */
@WebHandler
public class RegistrationController {

    @Route(method = HttpRequest.Method.GET, urlPattern = "/register")
    public HttpResponse GET(HttpRequest request){
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        try {
            String body = TemplateProcessor.process("register.html",null);
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }
}
