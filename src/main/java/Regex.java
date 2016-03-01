package main.java;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iShavgula on 16/02/16.
 */
public class Regex {
    private static Regex ourInstance = new Regex();

    public static Regex getInstance() {
        return ourInstance;
    }

    private Regex() {
    }

    /*
    public HashMap<String, String> headers(String text) {
        String pattern = "(.*)(\\d+)(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
        } else {
            System.out.println("NO MATCH");
        }

        return "";
    }*/
}
