package main.dar.server;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

import main.dar.server.annotation.Param;
import main.dar.server.annotation.Route;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
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

    private Router() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("main.dar.application"))
                .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Route.class);
        System.out.println(methods);

        for (Method m : methods) {
            System.out.println(m.getParameters().length);
            for(Parameter p : m.getParameters()){
                System.out.println(p.getParameterizedType());
                System.out.println(p.getAnnotation(Param.class));
                try {
                    String x = (String) p.getType().newInstance();
                    x = "123";
                    System.out.println("x : " + x);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

//        for (Method one : getMethodsAnnotatedWith(AbcTest.class, Route.class)) {
//            System.out.println(one.toString());
//        }
    }

    /*
    public List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    Annotation annotInstance = method.getAnnotation(annotation);
                    // TODO process annotInstance
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }*/
}
