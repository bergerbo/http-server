package main.dar.server;

import java.util.Date;

/**
 * Created by iShavgula on 15/03/16.
 */
public class Cookie {
    private String name;
    private String userAgent;
    private String host;
//    private Date expirationDate;
//    private String value;

    public Cookie(String name, HttpRequest request) {//, Date expirationDate) {
        this.name = name;
        this.userAgent = request.getHeader("User-Agent");
        this.host = request.getHeader("Host");
//        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public String hashValue() {
        // This could be more complex
        return ("[name]:" + name + "[userAgent]:" + userAgent+ "[host]:" + host).hashCode() + "";
    }

    public boolean isValid(String hashValue) {
        return hashValue.equals(hashValue());
    }


//    public Date getExpirationDate() {
//        return expirationDate;
//    }

//    public void setExpirationDate(Date expirationDate) {
//        this.expirationDate = expirationDate;
//    }

//    public String getValue() {
//        return value;
//    }

//    public void setValue(String value) {
//        this.value = value;
//    }

//    public boolean equals(Cookie cookie) {
//        return name.equals(cookie.getName()) &&
//                userAgent.equals(cookie.getUserAgent()) &&
//                ip.equals(cookie.getIp()) &&
//                expirationDate.equals(cookie.expirationDate) &&
//                value == cookie.value;
//   }
}
