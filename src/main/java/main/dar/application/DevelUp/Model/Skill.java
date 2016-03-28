package main.dar.application.DevelUp.Model;

/**
 * Created by iShavgula on 28/03/2016.
 */
public class Skill {
    public String name;
    public int experience;
    public int level;

    public Skill(String name, int experience, int level) {
        this.name = name;
        this.experience = experience;
        this.level = level;
    }

    public String getExperienceDescription() {
        switch (experience) {
            case 1:
                return "0-1 years";

            case 2:
                return "1-3 years";

            case 3:
                return "3-10 years";

            default:
                return "more than 10 years";
        }
    }

    public String getLevelDescription() {
        switch (level) {
            case 1:
                return "A";

            case 2:
                return "B";

            case 3:
                return "C";

            default:
                return "D";
        }
    }
}
