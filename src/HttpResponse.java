import java.util.Date;
import java.util.HashMap;

/**
 * Created by hoboris on 2/16/16.
 */
public class HttpResponse {
    private int statusCode;
    private String reason;
    private HashMap<String,String> headers;
    private String body;

    public HttpResponse(){
        headers = new HashMap<>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void addHeader(String header, String value) {
        headers.put(header,value);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }
}
