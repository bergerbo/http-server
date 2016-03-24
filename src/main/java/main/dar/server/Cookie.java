package main.dar.server;

import java.util.Date;

/**
 * Created by iShavgula on 15/03/16.
 */
public class Cookie {
    private String name;
    private String userAgent;
    private Date expirationDate;
    private String value;

    public Cookie(String name, String userAgent, Date expirationDate) {
        this.name = name;
        this.userAgent = userAgent;
        this.expirationDate = expirationDate;
        this.value = "-1";
    }

    public String hashValue() {
        // This could be more complex
        return ("[name]:" + name + "[value]:" + value + "[expirationDate]:" + expirationDate.toString()).hashCode() + "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Cookie cookie) {
        return name.equals(cookie.getName()) &&
                userAgent.equals(cookie.getUserAgent()) &&
                expirationDate.equals(cookie.expirationDate) &&
                value == cookie.value;
    }
}
