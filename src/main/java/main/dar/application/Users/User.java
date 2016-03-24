package main.dar.application.Users;

/**
 * Created by iShavgula on 24/03/16.
 */
public class User {
    private String name;
    private String password;
    private String description;

    public User(String name, String password, String description) {
        this.name = name;
        this.password = password;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return ("[Name : " + name + "][password : " + password + "]").hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User)obj;
        return name.equals(user.getName()) && password.equals(user.getPassword());
    }
}
