package com.example.utils;


import com.sun.jdi.InvocationException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class CustomJsonMapper {

    public static <T> T mapper(JSONObject object, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String s = object.toString().trim();
        s = s.replaceAll("[{}\"]", "");  // {, }, " 제거
        String[] pairs = s.split(",");

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        if (declaredConstructors.length > 1) {
            throw new RuntimeException("too many constructors");
        }

        Constructor<?> declaredConstructor = declaredConstructors[0];
        declaredConstructor.setAccessible(true);

        Object o = null;
        try {
            o = declaredConstructor.newInstance();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }

        @SuppressWarnings("unchecked")
        T instance = (T) o;


        JSONObject json = new JSONObject();

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();
                log.info("set"+capitalize(key));

                Method setter = clazz.getDeclaredMethod("set"+capitalize(key), String.class);
                setter.setAccessible(true);
                setter.invoke(instance,value);
            }
        }

        log.info("JSONObject object ::: {}", s);
        log.info("Class class ::: {}", clazz);
        return clazz.newInstance();
    }

    private static String lineMapper(String s) {
        s = s.replaceAll("[{}\"]", "");  // {, }, " 제거
        String[] pairs = s.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();
                System.out.println(key + " => " + value);
            }
        }
        return s;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
