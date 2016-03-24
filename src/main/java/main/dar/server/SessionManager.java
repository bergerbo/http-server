package main.dar.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by iShavgula on 15/03/16.
 */
public class SessionManager {
//    private static int expirationTime = 100000;

    private static SessionManager ourInstance = new SessionManager();
    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {

    }

    private boolean isCookieValid(Cookie cookie, HashMap<String, String> cookies) {
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            if (entry.getKey().equals(cookie.getName()) && entry.getValue().equals(cookie.hashValue())) {
                return true;
            }
        }

        return false;
    }

    public boolean areCookiesValid(ArrayList<String> requiredCookieNames, HttpRequest request) {
        for (String name : requiredCookieNames) {
            Cookie c = new Cookie(name, request);
            if (!isCookieValid(c, request.getCookies())) {
                return false;
            }
        }
        return true;
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
