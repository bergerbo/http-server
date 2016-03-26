package main.dar.server;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.glassfish.json.JsonPrettyGeneratorImpl;

import javax.json.*;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by hoboris on 3/25/16.
 */
public class TemplateProcessor {

    public static String process(String filename, JsonObject env) throws IOException {
        FileReader f = new FileReader(filename);
        String res = process(f, env);
        f.close();
        return res;
    }

    private static String process(Reader f, JsonObject env) throws IOException {
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

    private static String eval(String expression, JsonObject env) throws IOException {
        char c = expression.charAt(0);
        int index;
        String var;
        JsonValue val;
        String output;
        switch (c) {
            case '$':
                index = expression.indexOf(" ");
                if (index > 1)
                    var = expression.substring(1, index).trim();
                else
                    var = expression.substring(1).trim();

                val = resolve(var, env);
                if (val != null)
                    return display(val);
                break;
            case '?':
            case '!':
                index = expression.indexOf("=>");
                if (index > 1) {
                    var = expression.substring(1, index).trim();
                    val = resolve(var, env);
                    JsonValue.ValueType cond = c == '?' ? JsonValue.ValueType.TRUE : JsonValue.ValueType.FALSE;
                    if (val != null && val.getValueType() == cond) {
                        output = expression.substring(index + 2).trim();
                        String unEscaped = output.replace("{-", "{").replace("}-", "}");
                        StringReader reader = new StringReader(unEscaped);
                        return process(reader, env);
                    } else
                        return "";
                }
                break;
            case '@':
                index = expression.indexOf(":");
                int index2 = expression.indexOf("=>");
                if (index > 1 && index2 > index) {
                    var = expression.substring(1, index).trim();
                    String iteratorName = expression.substring(index + 1, index2).trim();
                    JsonValue iterator = resolve(iteratorName, env);
                    if (iterator.getValueType() == JsonValue.ValueType.ARRAY) {
                        output = expression.substring(index2 + 2).trim();
                        JsonArray array = (JsonArray) iterator;
                        String unEscaped = output.replace("{-", "{").replace("}-", "}");
                        StringBuilder sb = new StringBuilder();
                        for (JsonValue value : array) {
                            JsonObjectBuilder job = Json.createObjectBuilder();
                            for (Map.Entry<String,JsonValue> entry : env.entrySet()){
                                job.add(entry.getKey(),entry.getValue());
                            }
                            job.add(var,value);
                            sb.append(process(new StringReader(unEscaped), job.build()));
                        }
                        return sb.toString();
                    }

                }
                break;
            case '>':
                index = expression.indexOf(":");
                String file;
                JsonObject childEnv = Json.createObjectBuilder().build();
                if (index > 1){
                    file = expression.substring(1, index).trim();
                    var = expression.substring(index+1).trim();
                    val = resolve(var,env);
                    if(val.getValueType() == JsonValue.ValueType.OBJECT){
                        childEnv = (JsonObject) val;
                    }
                }
                else{
                    file = expression.substring(1).trim();
                }

                return process(file,childEnv);

        }
        return "";
    }

    private static JsonValue resolve(String variable, JsonObject env) {
        JsonObject scope = env;
        String[] parts = variable.split("\\.");
        if (parts.length > 0) {

            for (int i = 0; i < parts.length - 1; i++) {
                JsonValue val = scope.get(parts[i]);
                if (val.getValueType() == JsonValue.ValueType.OBJECT) {
                    scope = (JsonObject) val;
                } else
                    return JsonValue.NULL;
            }

            return scope.get(parts[parts.length - 1]);
        } else {
            return scope.get(variable);
        }
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
