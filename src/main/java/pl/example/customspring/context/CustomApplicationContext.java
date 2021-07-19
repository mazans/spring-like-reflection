package pl.example.customspring.context;

import pl.example.customspring.annotation.Bean;
import pl.example.customspring.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomApplicationContext {

    private final Map<String, Object> beans = new HashMap<>();

    public CustomApplicationContext(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Annotation> classAnnotations = List.of(clazz.getAnnotations());
        classAnnotations.stream()
                .filter(annotation -> annotation.annotationType() == Configuration.class)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not a config class"));

        List<Method> beanMethods = List.of(clazz.getMethods()).stream()
                .filter(this::hasBeanAnnotation)
                .collect(Collectors.toList());

        Constructor<?> constructor = clazz.getConstructor();
        Object configObject = constructor.newInstance();

        for(var beanMethod : beanMethods) {
            Bean beanAnnotation = beanMethod.getAnnotation(Bean.class);
            String beanName = beanAnnotation.name().equals("") ? beanMethod.getName() : beanAnnotation.name();
            beans.put(beanName, beanMethod.invoke(configObject));
        }
    }

    private boolean hasBeanAnnotation(Method method) {
        for (var annotation : method.getAnnotations()) {
            if(annotation.annotationType() == Bean.class) {
                return true;
            }
        }
        return false;
    }

    public Object getBeanByName(String name) {
        return beans.get(name);
    }
}
