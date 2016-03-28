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
        users.addAll(getRandomUsers());
    }

    public void addUser(User user) {
//        user.addSkill(new Skill("Skill 1", 1, 2));
//        user.addSkill(new Skill("Skill 2", 2, 3));
//        user.addSkill(new Skill("Skill 3", 1, 4));
//        user.addSkill(new Skill("Skill 4", 3, 1));
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

    private ArrayList<User> getRandomUsers() {
        ArrayList<User> users = new ArrayList<>();

        User user1 = new User(getNewId(), "Giorgi", "Shavgulidze", "giorgishavgulidze@gmail.com", "123");
        user1.addSkill(new Skill("Skill 1", 4, 1));
        user1.addSkill(new Skill("Skill 2", 2, 3));
        user1.addSkill(new Skill("Skill 3", 4, 2));

        User user2 = new User(getNewId(), "Boris", "Berger", "borisberger@gmail.com", "123");
        user2.addSkill(new Skill("Skill 1", 4, 2));
        user2.addSkill(new Skill("Skill 2", 3, 1));
        user2.addSkill(new Skill("Skill 3", 1, 4));

        User user3 = new User(getNewId(), "Super", "Man", "superman@gmail.com", "123");
        user3.addSkill(new Skill("Skill 1", 1, 2));
        user3.addSkill(new Skill("Skill 2", 2, 3));

        User user4 = new User(getNewId(), "Bat", "Man", "batman@gmail.com", "123");
        user4.addSkill(new Skill("Skill 1", 1, 2));
        user4.addSkill(new Skill("Skill 2", 2, 3));

        User user5 = new User(getNewId(), "Spider", "Man", "spiderman@gmail.com", "123");
        user5.addSkill(new Skill("Skill 1", 1, 2));

        User user6 = new User(getNewId(), "Sam", "Sung", "samsung@gmail.com", "123");
        user6.addSkill(new Skill("Skill 1", 1, 2));

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        return users;
    }

    public JsonObjectBuilder getAllUsersData() {
        JsonObjectBuilder json = Json.createObjectBuilder();

        JsonArrayBuilder usersJson = Json.createArrayBuilder();
        int styleIdx = 1;
        users.sort((u1,u2)-> (u1.getSkills().size() < u2.getSkills().size()) ? 1 : -1);
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
