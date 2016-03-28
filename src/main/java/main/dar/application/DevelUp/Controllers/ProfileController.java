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
    public HttpResponse GET(HttpRequest request){
        ArrayList<String> requiredCookies = new ArrayList<String>(){{
            add("auth");
        }};

        if (!SessionManager.getInstance().areCookiesValid(requiredCookies, request)) {
            HttpResponse res = new HttpResponse("auth cookie wasn't found!", 403);
            return res;
        }

        String sessionId = SessionManager.getSessionIdForRequest(request);
        Integer userId= (Integer)SessionManager.getInstance().getSessionInfo(sessionId);
        if (userId == null) {
            return new HttpResponse("Session is closed", 401);
        }

        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);


        try {
            String body = TemplateProcessor.process("profile.html", getJsonDataForUser(userId).build());
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
                                    @Param("skillLevel") int skillLevel){
        ArrayList<String> requiredCookies = new ArrayList<String>(){{
            add("auth");
        }};

        if (!SessionManager.getInstance().areCookiesValid(requiredCookies, request)) {
            HttpResponse res = new HttpResponse("auth cookie wasn't found!", 403);
            return res;
        }

        String sessionId = SessionManager.getSessionIdForRequest(request);
        Integer userId= (Integer)SessionManager.getInstance().getSessionInfo(sessionId);
        if (userId == null) {
            return new HttpResponse("Session is closed", 401);
        }

        Skill newSkill = new Skill(skill, skillExperience, skillLevel);
        DB.getInstance().addSkill(userId, newSkill);

        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);


        try {
            String body = TemplateProcessor.process("profile.html", getJsonDataForUser(userId).build());
            response.setBody(body);
        } catch (IOException e) {
            response.setStatusCode(500);
        }

        return response;
    }

    private JsonObjectBuilder getJsonDataForUser(int userId) {
        User user = DB.getInstance().getUser(userId);

        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("name", user.fullName());

        JsonArrayBuilder skills = Json.createArrayBuilder();
        for (Skill skill : user.getSkills()) {
            JsonObjectBuilder s = Json.createObjectBuilder();
            s.add("name", skill.name);
            s.add("experience", skill.getExperienceDescription());
            s.add("level", skill.getLevelDescription());
            skills.add(s);
        }

        json.add("skills", skills);

        return json;
    }
}
