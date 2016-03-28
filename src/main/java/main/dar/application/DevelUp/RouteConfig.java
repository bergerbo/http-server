package main.dar.application.DevelUp;

import main.dar.server.interfaces.RouterConfig;

/**
 * Created by hoboris on 3/28/16.
 */
public class RouteConfig implements RouterConfig {

    @Override
    public String applicationRoot() {
        return "develUp";
    }
}
