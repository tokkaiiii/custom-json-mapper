package com.example.utils;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.example.utils.TypeConverter.*;

@Slf4j
public class CustomJsonMapper {

    public static final String SETTER_PREFIX = "set";

    public static <T> T mapper(JSONObject object, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        String s = object.toString().trim();
        s = s.replaceAll("[{}\"]", "");  // {, }, " 제거
        String[] pairs = s.split(",");

        T instance = getT(clazz);

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();

                Field declaredField = clazz.getDeclaredField(key);
                Class<?> fieldType = declaredField.getType();

                Method setterMethod = clazz.getDeclaredMethod(SETTER_PREFIX + capitalize(key), fieldType);
                setterMethod.invoke(instance, convert(value, fieldType));
            }
        }

        log.info("JSONObject object ::: {}", s);
        log.info("Class class ::: {}", clazz);
        return instance;
    }

    private static <T> T getT(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        if (declaredConstructors.length > 1) {
            throw new RuntimeException("too many constructors");
        }

        Constructor<?> declaredConstructor = declaredConstructors[0];

        Object o = null;
        try {
            o = declaredConstructor.newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }

        @SuppressWarnings("unchecked")
        T instance = (T) o;
        return instance;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
