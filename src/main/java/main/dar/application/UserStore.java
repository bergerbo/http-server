package main.dar.application;

import main.dar.server.HttpRequest;
import main.dar.server.HttpResponse;
import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;

import java.util.HashMap;

/**
 * Created by iShavgula on 24/03/16.
 */

@WebHandler
public class UserStore {
//    private static UserStore ourInstance = new UserStore();
//    public static UserStore getInstance() {
//        return ourInstance;
//    }

    private HashMap<String, User> users;

    public UserStore() {
        users = new HashMap<>();
    }

    public boolean userExists(String name) {
        return users.get(name) != null;
    }

    public void removeUser(String name) {
        users.remove(name);
    }

    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public String getDescriptionForUser(String name) {
        return users.get(name).getDescription();
    }

    @Route(method = HttpRequest.Method.POST, urlPattern = "/register")
    public HttpResponse register(@Param("name") String name,
                                 @Param("password") String password,
                                 @Param("description") String description) {
        if (userExists(name.toLowerCase())) {
            return new HttpResponse("User already exists", 401);
        }
        addUser(new User(name, password, description));
        HttpResponse res = new HttpResponse("Successfully created an user", 200);

        return null;
    }

}
