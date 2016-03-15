package main.dar.server;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpResponse {

    private String body;
    private int statusCode;


    public HttpResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }


    private String statusCodeMessage() {
        if (200 >= statusCode && statusCode < 300) {
            return "OK";
        }
        if (300 >= statusCode && statusCode < 400) {
            return "BAAAAAAD";
        }
        if (400 >= statusCode && statusCode < 500) {
            return "Holy FUCK";
        }
        return "Unreal";
    }

    private String headers() {
        return "HTTP/1.1 " + Integer.toString(statusCode) + " " + statusCodeMessage() + "\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.length() + "\r\n\r\n";
    }

    public String content() {
        return headers() + body;
    }
}
