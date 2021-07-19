package pl.example.customspring.program;

import pl.example.customspring.context.CustomApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var context = new CustomApplicationContext(MyConfiguration.class);
        System.out.println(context.getBeanByName("stringBean"));
    }
}
