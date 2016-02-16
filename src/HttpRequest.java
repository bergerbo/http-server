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
    private HashMap<String,String> headers;

    private String body;
}
