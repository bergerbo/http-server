package main.dar.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpResponse {

    private String body;
    private int statusCode;
    private HashMap<String, String> headers;

    public HttpResponse() {
        this.body = "Internal error";
        statusCode = 500;
        headers = new HashMap<>();
    }

    public HttpResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        headers = new HashMap<>();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private String getStartingHeaders() {
        return "HTTP/1.1 " + Integer.toString(statusCode) + " " + statusCodeMessage() + "\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.length() + "\r\n";
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(Cookie c) {
        headers.put("Set-Cookie", c.getName() + "=" + c.hashValue());
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
        String res = getStartingHeaders();
        for (Map.Entry header : headers.entrySet()) {
            res += header.getKey() + ": " + header.getValue() + "\r\n";
        }

        res += "\r\n";

        return res;
    }

    public String content() {
        return headers() + body;
    }
}
