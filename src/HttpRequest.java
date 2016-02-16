import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpRequest {
    public enum Method {
        GET, POST, PUT, CREATE
    }

    private Method method;
    private HashMap<String, String> headers;
    private String body;

    public static HttpRequest parse(BufferedReader in) throws IOException {
        String methodLine = in.readLine();
        System.out.println(methodLine);

        String[] methods = methodLine.split(" ");

        HttpRequest req = new HttpRequest();
        req.setMethod(HttpRequest.Method.valueOf(methods[0]));
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

}
