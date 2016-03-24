package main.dar.server;

import main.dar.server.Types.ServiceType;
import main.dar.server.exception.BadlyFormedHttpRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iShavgula on 15/03/16.
 */
public class SessionManager {
    private static int expirationTime = 100000;

    private static SessionManager ourInstance = new SessionManager();
    public static SessionManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, Cookie> cookies;

    private SessionManager() {
        cookies = new HashMap<>();
    }

    public void addCookie(Cookie cookie) {
        if (cookies.containsKey(cookie.hashCode())) {
            return;
        }

        cookies.put(cookie.hashValue(), cookie);
    }

    public boolean haveCookie(Cookie cookie) {
        return cookie.equals(cookies.get(cookie.hashValue()));
    }
/*
    static public void process(HttpRequest request, HttpResponse response, HashSet<ServiceType> services) throws BadlyFormedHttpRequest, ParseException {
        for (ServiceType service : services) {
            if (request.getHeader("Cookie") == null) {
                String userAgent = request.getHeader("User-Agent");
                if (userAgent == null) throw new BadlyFormedHttpRequest();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String dateInString = "22-01-2015 10:20:56";
                Date date = sdf.parse(dateInString);
                Date expirationDate = new Date(date.getTime() + expirationTime);

                Cookie cookie = new Cookie(serviceType.serviceName(), userAgent, expirationDate);
                cookie.setValue(cookie.hashValue());

                response.addHeader("Set-Cookie", cookie.getName() + "=\"" + cookie.getValue() + "\"");
            }
        }
//        else {
//            response.addHeader("Cookie", request.getHeader("Cookie"));
//        }
    }*/

}
