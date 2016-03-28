package main.dar.application.DevelUp.Controllers;

import main.dar.application.DevelUp.Model.DB;
import main.dar.application.DevelUp.Model.User;
import main.dar.server.*;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import java.io.IOException;

/**
 * Created by iShavgula on 28/03/2016.
 */

@WebHandler
public class LoginController {
    @Route(method = HttpRequest.Method.POST, urlPattern = "/login")
    public HttpResponse login(HttpRequest request,
                                 @Param("email") String email,
                                 @Param("password") String password){


        User user = DB.getInstance().getUserWithEmailAndPassword(email, password);

        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        if (user == null) {
            response.setStatusCode(401);
            response.setBody("user wasn't found in the db");

            return response;
        }

        Cookie c = new Cookie("auth", request);
        response.addCookie(c);

        String sessionId = SessionManager.getSessionIdForRequest(request);
        SessionManager.getInstance().addSession(sessionId, new Integer(user.id));

        try {
            String body = TemplateProcessor.process("profile.html", user.getJsonData().build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }


    @Route(method = HttpRequest.Method.GET, urlPattern = "/logout")
    public HttpResponse logout(HttpRequest request){
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        String sessionId = SessionManager.getSessionIdForRequest(request);
        SessionManager.getInstance().closeSession(sessionId);

        try {
            String body = TemplateProcessor.process("register.html", null);
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }

}
