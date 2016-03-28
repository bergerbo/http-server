package main.dar.application.DevelUp.Controllers;

import main.dar.application.DevelUp.Model.DB;
import main.dar.application.DevelUp.Model.User;
import main.dar.application.Users.UserSession;
import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
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

    @Route(method = HttpRequest.Method.POST, urlPattern = "/register")
    public HttpResponse register(HttpRequest request,
                            @Param("name") String name,
                            @Param("surname") String surname,
                            @Param("email") String email,
                            @Param("password") String password){

        User user = new User(DB.getInstance().getNewId(), name, surname, email, password);
        DB.getInstance().addUser(user);

        HttpResponse response = new HttpResponse("Successfully created an user", 200);

        Cookie c = new Cookie("auth", request);
        response.addCookie(c);

        String sessionId = SessionManager.getSessionIdForRequest(request);
        SessionManager.getInstance().addSession(sessionId, new Integer(user.id));

        try {
            String body = TemplateProcessor.process("develUp/profile.html", user.getJsonData().build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }
}
