package com.example.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class TypeConverter {
    private static final Map<Class<?>, Function<String, Object>> converters = new HashMap<>();

    static {
        converters.put(int.class, Integer::parseInt);
        converters.put(Integer.class, Integer::valueOf);
        converters.put(long.class, Long::parseLong);
        converters.put(Long.class, Long::valueOf);
        converters.put(double.class, Double::parseDouble);
        converters.put(Double.class, Double::valueOf);
        converters.put(boolean.class, Boolean::parseBoolean);
        converters.put(Boolean.class, Boolean::valueOf);
        converters.put(String.class, s -> s);
        converters.put(LocalDate.class, LocalDate::parse);
    }

    public static Object convert(String value, Class<?> targetType) {
        Function<String, Object> converter = converters.get(targetType);
        if (converter != null) {
            return converter.apply(value);
        }

        throw new IllegalArgumentException("Cannot convert " + value + " to " + targetType);
    }
}
