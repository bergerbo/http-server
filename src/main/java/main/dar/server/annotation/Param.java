package main.dar.server.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by iShavgula on 01/03/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    String value();
    boolean isOptional() default false;
    boolean isUrlParam() default false;
}
