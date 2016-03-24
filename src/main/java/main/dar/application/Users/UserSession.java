package main.dar.application.Users;

/**
 * Created by iShavgula on 24/03/16.
 */
public class UserSession {
    private String name;

    public UserSession(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
