package main.dar.application.DevelUp.Model;

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

}
