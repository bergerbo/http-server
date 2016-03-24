package main.dar.server.Types;

/**
 * Created by iShavgula on 24/03/16.
 */
public enum ServiceType {
    Authentification;

    public String serviceName() {
        switch (this) {
            case Authentification:
                return "Auth";
        }
        return "";
    }
}
