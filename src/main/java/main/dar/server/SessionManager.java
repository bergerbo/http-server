package main.dar.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by iShavgula on 15/03/16.
 */
public class SessionManager {
    private static int expirationTime = 600000;

    private static SessionManager ourInstance = new SessionManager();
    public static SessionManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, Object> sessions;
    private HashMap<String, Date> dateExpiration;

    private SessionManager() {
        sessions = new HashMap<>();
        dateExpiration = new HashMap<>();
    }

    static public String getSessionIdForRequest(HttpRequest request) {
        String host = request.getHeader("Host");
        String userAgent = request.getHeader("User-Agent");
        return ("[host]:" + host + "[user-agent]:" + userAgent).hashCode() + "";
    }

    public void addSession(String id, Object sessionInfo) {
        sessions.put(id, sessionInfo);

        Date d = new Date();
        d.setTime(d.getTime() + expirationTime);

        dateExpiration.put(id, d);
        sessions.put(id, sessionInfo);
    }

    public Object getSessionInfo(String id) {
        if (dateExpiration.get(id) == null) return null;
        if (dateExpiration.get(id).before(new Date())) {
            sessions.remove(id);
            dateExpiration.remove(id);
            return null;
        }

        dateExpiration.get(id).setTime((new Date()).getTime() + expirationTime);

        return sessions.get(id);
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
}
