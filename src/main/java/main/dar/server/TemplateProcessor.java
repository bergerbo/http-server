package main.dar.server;

import jdk.nashorn.internal.runtime.JSONFunctions;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by hoboris on 3/25/16.
 */
public class TemplateProcessor {

    public static String process(String filename, JsonObject env) throws IOException {
        FileReader f = new FileReader(filename);
        StringBuilder out = new StringBuilder();
        StringBuilder exp = new StringBuilder();
        boolean inExpr = false;

        int input;
        while ((input = f.read()) != -1) {
            char c = (char) input;
            if (!inExpr) {
                out.append(c);
                int index;
                if ((index = out.indexOf("{{")) > 0) {
                    out.delete(index, index + 2);
                    inExpr = true;
                }

            } else {
                exp.append(c);
                int index;
                if ((index = exp.indexOf("}}")) > 0) {
                    exp.delete(index, index + 2);
                    inExpr = false;
                    out.append(eval(exp.toString().trim(), env));
                    exp = new StringBuilder();
                }

            }

        }
        return out.toString();
    }

    private static String eval(String expression, JsonObject env) {
        char c = expression.charAt(0);
        int index;
        String var;
        JsonValue val;
        switch (c) {
            case '$':
                index = expression.indexOf(" ");
                if (index > 1)
                    var = expression.substring(1, index);
                else
                    var = expression.substring(1);

                val = env.get(var);
                if (val != null)
                    return display(val);
                break;
            case '?':
            case '!':
                index = expression.indexOf("=>");
                String output;
                if (index > 1) {
                    var = expression.substring(1, index).trim();
                    output = expression.substring(index + 2);
                    val = env.get(var);
                    JsonValue.ValueType cond = c == '?' ? JsonValue.ValueType.TRUE : JsonValue.ValueType.FALSE;
                    if (val != null && val.getValueType() == cond) {
                        return output;
                    } else
                        return "";
                }
                break;

        }
        return "EVAL";
    }

    private static String display(JsonValue val) {
        switch (val.getValueType()) {
            case ARRAY:
                break;
            case OBJECT:
                break;
            case STRING:
                return val.toString().replace("\"", "");
            case NUMBER:
                break;
            case TRUE:
                break;
            case FALSE:
                break;
            case NULL:
                break;
        }
        return val.toString();
    }
}
