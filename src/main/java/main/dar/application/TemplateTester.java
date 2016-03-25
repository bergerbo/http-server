package main.dar.application;

import main.dar.server.HttpRequest;
import main.dar.server.HttpResponse;
import main.dar.server.TemplateProcessor;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iShavgula on 01/03/16.
 */
@WebHandler
public class TemplateTester {

    @Route(method = HttpRequest.Method.GET, urlPattern = "/test1")
    public HttpResponse ListPoints(HttpRequest request) {

        String body = null;
        try {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("chocolate","INSANE");
            job.add("nomnom",true);
            job.add("enjoy",false);
            body = TemplateProcessor.process("test.html", job.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpResponse res = new HttpResponse(body, 200);
        return res;
    }


}
