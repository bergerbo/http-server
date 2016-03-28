package main.dar.application.DevelUp.Model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;

/**
 * Created by iShavgula on 28/03/2016.
 */
public class User {
    public int id;
    public String name;
    public String surname;
    public String email;
    public String password;
    private ArrayList<Skill> skills;

    public User(int id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.skills = new ArrayList<>();
    }

    public String fullName() {
        return name + " " + surname;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void addSkills(ArrayList<Skill> skills) {
        this.skills.addAll(skills);
    }

    public JsonObjectBuilder getJsonData() {
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("name", fullName());

        JsonArrayBuilder skills = Json.createArrayBuilder();
        for (Skill skill : getSkills()) {
            JsonObjectBuilder s = Json.createObjectBuilder();
            s.add("name", skill.name);
            s.add("experience", skill.getExperienceDescription());
            s.add("level", skill.getLevelDescription());
            skills.add(s);
        }

        json.add("skills", skills);

        return json;
    }
 }
