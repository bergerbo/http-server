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
}
