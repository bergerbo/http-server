package main.dar.server.annotation.Types;

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
}
