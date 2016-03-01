package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpRequest {


    public enum Method {
        GET, POST, PUT, CREATE
    }

    private Method method;
    private String url;
    private String protocol;
    private HashMap<String, String> headers;
    private String body;

    public static HttpRequest parse(BufferedReader in) throws IOException, BadlyFormedHttpRequest {
        String methodLine = in.readLine();
        System.out.println(methodLine);

        String[] request = methodLine.split(" ");

        if(request.length != 3)
            throw new BadlyFormedHttpRequest();

        HttpRequest req = new HttpRequest();
        req.setMethod(HttpRequest.Method.valueOf(request[0]));
        req.setUrl(request[1]);
        req.setProtocol(request[2]);


        String headerLine;
        while (!(headerLine = in.readLine()).isEmpty() && headerLine != null) {
            System.out.println(headerLine);
            String[] parsedHeader = headerLine.split(": ");
            req.addHeader(parsedHeader[0], parsedHeader[1]);
            System.out.println("header :" + parsedHeader[0] + " : " + parsedHeader[1]);
        }

        StringBuffer body = new StringBuffer();

        String bodyLine;
        if (in.ready()) {
            while (!(bodyLine = in.readLine()).isEmpty() && bodyLine != null) {
                body.append(bodyLine);
                System.out.println("bodyline :" + bodyLine);
            }
            req.setBody(body.toString());
        }

        return req;
    }


    public HttpRequest() {
        headers = new HashMap<>();
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
        StringBuffer str = new StringBuffer();

        str.append(method.toString());
        str.append(" ");
        str.append(url);
        str.append(" ");
        str.append(protocol);
        str.append("\n");

        for (Map.Entry header: headers.entrySet()) {
            str.append(header.getKey());
            str.append("");
            str.append(header.getValue());
            str.append("\n");
        }

        str.append("\n");
        str.append(body);

        return str.toString();
    }

    public String toHtml() {
        StringBuffer str = new StringBuffer();

        str.append(method.toString());
        str.append(" ");
        str.append(url);
        str.append(" ");
        str.append(protocol);
        str.append("\n");

        for (Map.Entry header: headers.entrySet()) {
            str.append(header.getKey());
            str.append("");
            str.append(header.getValue());
            str.append("\n");
        }

        str.append("\n");
        str.append(body);

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

        for (Map.Entry header: headers.entrySet()) {
            str.append(header.getKey());
            str.append("");
            str.append(header.getValue());
            str.append("\n");
        }

        str.append("\n");
        str.append(body);

        return str.toString();
    }

}
