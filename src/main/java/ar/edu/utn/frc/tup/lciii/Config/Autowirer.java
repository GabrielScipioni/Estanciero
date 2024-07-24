package ar.edu.utn.frc.tup.lciii.Config;

import ar.edu.utn.frc.tup.lciii.Config.Anotations.Autowire;


import java.lang.reflect.Field;

public class Autowirer {

    public static void injectDependencies(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowire.class)) {
                field.setAccessible(true);
                try {
                    // Si el campo ya está inicializado, no lo sobrescribas
                    if (field.get(object) != null) {
                        continue;
                    }

                    // Crear una nueva instancia del tipo de campo
                    Object dependency = createInstance(field.getType());
                    field.set(object, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to inject dependency: " + field.getType().getName(), e);
                }
            }
        }
    }

    private static Object createInstance(Class<?> clase) {
        try {
            return clase.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create instance of class: " + clase.getName(), e);
        }
    }
}