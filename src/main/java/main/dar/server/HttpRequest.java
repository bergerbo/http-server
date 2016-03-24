package main.dar.server;

import main.dar.server.exception.BadlyFormedHttpRequest;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpRequest {


    public HashMap<String, String> getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(HashMap<String, String> urlParameters) {
        this.urlParameters = urlParameters;
    }

    public enum Method {
        GET, POST, PUT, DELETE, CREATE
    }

    private Method method;
    private String url;
    private String protocol;
    private HashMap<String, String> headers;
    private HashMap<String, String> cookies;
    private HashMap<String, String> parameters;
    private HashMap<String, String> urlParameters;
    private String body;

    public static HttpRequest parse(BufferedReader in) throws IOException, BadlyFormedHttpRequest {
        String methodLine = in.readLine();
//        System.out.println(methodLine);

        String[] request = methodLine.split(" ");

        if (request.length != 3)
            throw new BadlyFormedHttpRequest();

        HttpRequest req = new HttpRequest();
        req.setMethod(HttpRequest.Method.valueOf(request[0]));
        req.setUrl(request[1]);
        req.setProtocol(request[2]);


        String headerLine;
        while (!(headerLine = in.readLine()).isEmpty() && headerLine != null) {
            System.out.println(headerLine);
            String[] parsedHeader = headerLine.split(": ");
            if (parsedHeader[0].equals("Cookie")) {
                String [] parsedCookies = parsedHeader[1].split(";");
                for (String c : parsedCookies) {
                    if (!c.equals("")) {
                        String[] parsedCookie = c.split("=");
                        req.addCookie(parsedCookie[0], parsedCookie[1]);
                    }
                }
            } else {
                req.addHeader(parsedHeader[0], parsedHeader[1]);
            }
            System.out.println("header :" + parsedHeader[0] + " : " + parsedHeader[1]);
        }

        StringBuffer body = new StringBuffer();

        while (in.ready()) {
            char c = (char) in.read();
            body.append(c);
        }
        req.setBody(body.toString());
        req.parseParameters();

        return req;
    }


    public HttpRequest() {
        headers = new HashMap<>();
        parameters = new HashMap<>();
        urlParameters = new HashMap<>() ;
        cookies = new HashMap<>();
    }


    private void parseParameters() {
        String parametersString = null;


        if(method == Method.POST){
            parametersString = body;
        } else {
            String[] split = url.split("\\?");
            if(split.length == 2){
                url = split[0];
                parametersString = split[1];
            }
        }

        if(parametersString != null){
            String[] kvps = parametersString.split("&");
            for (String kvp: kvps) {
                String[] pair = kvp.split("=");
                if(pair.length != 2)
                    continue;

                parameters.put(pair[0],pair[1]);
            }
        }

    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String toJson() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("method", method.toString());
        job.add("url", url);
        job.add("protocol", protocol);


        if (headers.size() > 0) {

            JsonObjectBuilder hob = Json.createObjectBuilder();
            for (Map.Entry<String, String> header : headers.entrySet()) {
                hob.add(header.getKey(), header.getValue());
            }

            job.add("headers", hob);
        }

        if (body != null)
            job.add("body", body);

        return job.build().toString();
    }

    public String toHtml() {
        StringBuffer str = new StringBuffer();

        str.append("<html><head><title>" + method.toString() + " " + url + "</title></head><body>");
        str.append("<h1>");
        str.append("\n");
        str.append(method.toString());
        str.append(" ");
        str.append(url);
        str.append(" ");
        str.append(protocol);
        str.append("\n");
        str.append("</h1>");

        if (headers.size() > 0) {

            str.append("<table><tr><th>Header</th><th>Value</th></tr>");
            for (Map.Entry header : headers.entrySet()) {
                str.append("<tr>");

                str.append("<td>");
                str.append(header.getKey());
                str.append("</td>");

                str.append("<td>");
                str.append(header.getValue());
                str.append("</td>");

                str.append("</tr>");
            }
            str.append("</table>");
        }

        if (body != null) {

            str.append("<div>");
            str.append(body);
            str.append("</div>");
        }

        str.append("</body></html>");
        return str.toString();
    }

    public String toText() {
        StringBuffer str = new StringBuffer();

        str.append(method.toString());
        str.append(" ");
        str.append(url);
        str.append(" ");
        str.append(protocol);
        str.append("\n");

        for (Map.Entry header : headers.entrySet()) {
            str.append(header.getKey());
            str.append(": ");
            str.append(header.getValue());
            str.append("\n");
        }

        str.append("\n");
        if (body != null)
            str.append(body);

        return str.toString();
    }

}
