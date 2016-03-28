package main.dar.application.DevelUp.Controllers;

import main.dar.application.DevelUp.Model.DB;
import main.dar.application.DevelUp.Model.Skill;
import main.dar.application.DevelUp.Model.User;
import main.dar.server.HttpRequest;
import main.dar.server.HttpResponse;
import main.dar.server.SessionManager;
import main.dar.server.TemplateProcessor;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by iShavgula on 28/03/2016.
 */

@WebHandler
public class ProfileController {

    @Route(method = HttpRequest.Method.GET, urlPattern = "/profile")
    public HttpResponse GET(HttpRequest request) {
        ArrayList<String> requiredCookies = new ArrayList<String>() {{
            add("auth");
        }};

        if (!SessionManager.getInstance().areCookiesValid(requiredCookies, request)) {
            return HttpResponse.redirect("/register");
//            HttpResponse res = new HttpResponse("auth cookie wasn't found!", 403);
//            return res;
        }

        String sessionId = SessionManager.getSessionIdForRequest(request);
        Integer userId = (Integer) SessionManager.getInstance().getSessionInfo(sessionId);
        if (userId == null) {
            return HttpResponse.redirect("/register");
//            return new HttpResponse("Session is closed", 401);
        }

        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        User user = DB.getInstance().getUser(userId);

        try {
            String body = TemplateProcessor.process("profile.html", user.getJsonData().build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }

    @Route(method = HttpRequest.Method.POST, urlPattern = "/profile/addnewskill")
    public HttpResponse addNewSkill(HttpRequest request,
                                    @Param("skill") String skill,
                                    @Param("skillExperience") int skillExperience,
                                    @Param("skillLevel") int skillLevel) {
        ArrayList<String> requiredCookies = new ArrayList<String>() {{
            add("auth");
        }};

        if (!SessionManager.getInstance().areCookiesValid(requiredCookies, request)) {
            return HttpResponse.redirect("/register");
//            HttpResponse res = new HttpResponse("auth cookie wasn't found!", 403);
//            return res;
        }

        String sessionId = SessionManager.getSessionIdForRequest(request);
        Integer userId = (Integer) SessionManager.getInstance().getSessionInfo(sessionId);
        if (userId == null) {
            return HttpResponse.redirect("/register");
//            return new HttpResponse("Session is closed", 401);
        }

        Skill newSkill = new Skill(skill, skillExperience, skillLevel);
        DB.getInstance().addSkill(userId, newSkill);

        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        User user = DB.getInstance().getUser(userId);

        try {

            String body = TemplateProcessor.process("profile.html", user.getJsonData().build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }
}
