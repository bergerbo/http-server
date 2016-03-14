package main.dar.server.Types;

import main.dar.server.annotation.Param;

/**
 * Created by iShavgula on 08/03/16.
 */
public class ParamType {
    private Param param;
    private Class<?> type;

    public ParamType(Param param, Class<?> type) {
        this.setParam(param);
        this.setType(type);
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isString() {
        if (type.toString().equals(String.class.toString())) {
            return true;
        }

        return false;
    }

    public boolean isInteger() {
        if (type.toString().equals(Integer.class.toString()) ||
                type.toString().equals(int.class.toString())) {
            return true;
        }

        return false;
    }

    public boolean isDouble() {
        if (type.toString().equals(Double.class.toString()) ||
                type.toString().equals(double.class.toString())) {
            return true;
        }

        return false;
    }

    public boolean isFloat() {
        if (type.toString().equals(Float.class.toString()) ||
                type.toString().equals(float.class.toString())) {
            return true;
        }

        return false;
    }

    public boolean isBoolean() {
        if (type.toString().equals(Boolean.class.toString()) ||
                type.toString().equals(boolean.class.toString())) {
            return true;
        }

        return false;
    }
}
