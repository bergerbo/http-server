package main.dar.server;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import main.dar.server.annotation.WebHandler;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Created by iShavgula on 01/03/16.
 */
public class Router {
    private static Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }

    private static ArrayList<RouteBinding> bindings = new ArrayList<RouteBinding>();

    private Router() {
        try {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage("main.dar.application"))
                    .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));
            Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(WebHandler.class);
            System.out.println(handlers);

            for (Class<?> handler : handlers) {
                Method[] methods = handler.getMethods();
                Object handlerInstance = handler.newInstance();

                for (Method m : methods) {
                    Route route;
                    if ((route = m.getDeclaredAnnotation(Route.class)) != null) {

                        RouteBinding binding = new RouteBinding(handlerInstance, m, route.method(), route.url());

                        for (Parameter p : m.getParameters()) {
                            Param httpParam;
                            if ((httpParam = p.getDeclaredAnnotation(Param.class)) != null)
                                binding.addParam(httpParam);
                        }

                        bindings.add(binding);
                    }
                }

            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public RouteBinding match(HttpRequest request){
        for (RouteBinding binding: bindings) {
            if(binding.match(request)){
                return binding;
            }
        }
        return null;
    }
}
