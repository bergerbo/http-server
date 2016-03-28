package main.dar.application.DevelUp.Model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;

/**
 * Created by iShavgula on 28/03/2016.
 */
public class DB {
    private static DB ourInstance = new DB();

    public static DB getInstance() {
        return ourInstance;
    }

    private ArrayList<User> users;

    private DB() {
        users = new ArrayList<>();

        for (int i = 0; i < 5; i ++) {
            users.add(getRandomUser());
        }
    }

    public void addUser(User user) {
        user.addSkill(new Skill("Skill 1", 1, 2));
        user.addSkill(new Skill("Skill 2", 2, 3));
        user.addSkill(new Skill("Skill 3", 1, 4));
        user.addSkill(new Skill("Skill 4", 3, 1));
        users.add(user);
    }

    public void addSkill(int userId, Skill skill) {
        getUser(userId).addSkill(skill);
    }

    public User getUser(int id) {
        for (User user : users) {
            if (user.id == id) {
                return user;
            }
        }
        return null;
    }

    public User getUserWithEmailAndPassword(String email, String password) {
        for (User user : users) {
            if (user.email.toLowerCase().equals(email.toLowerCase()) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }

    public int getNewId() {
        int id = 0;
        for (User user : users) {
            if (id < user.id) {
                id = user.id;
            }
        }

        return ++id;
    }

    private User getRandomUser() {
        User user = new User(getNewId(), "name", "surname", "123@gmail.com", "123");
        user.addSkill(new Skill("Skill 1", 1, 2));
        user.addSkill(new Skill("Skill 2", 2, 3));
        user.addSkill(new Skill("Skill 3", 1, 4));
        user.addSkill(new Skill("Skill 4", 3, 1));

        return user;
    }

    public JsonObjectBuilder getAllUsersData() {
        JsonObjectBuilder json = Json.createObjectBuilder();

        JsonArrayBuilder usersJson = Json.createArrayBuilder();
        int styleIdx = 1;
        for (User user: users) {
            JsonObjectBuilder userJson = Json.createObjectBuilder();
            userJson.add("fullName", user.fullName());

            JsonArrayBuilder skillsJson = Json.createArrayBuilder();
            for (Skill skill : user.getSkills()) {
                JsonObjectBuilder skillJson = Json.createObjectBuilder();
                skillJson.add("name", skill.name);
                skillJson.add("experience", skill.getExperienceDescription());
                skillJson.add("level", skill.getLevelDescription());
                skillsJson.add(skillJson);
            }

            userJson.add("skills", skillsJson);
            userJson.add("style", styleIdx);

            usersJson.add(userJson);

            styleIdx = styleIdx % 6 + 1;
        }

        json.add("users", usersJson);

        return json;
    }

}
