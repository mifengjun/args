package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> argList = List.of(args);

            // 获取定义的构造函数
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map(p -> parseOption(p, argList)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> argList) {
        Option annotation = parameter.getAnnotation(Option.class);
        // 传入 true 参数，用构造函数创建对象
        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = argList.contains("-" + annotation.value());
        }
        if (parameter.getType() == int.class) {
            int index = argList.indexOf("-" + annotation.value());
            value = Integer.valueOf(argList.get(index + 1));
        }
        if (parameter.getType() == String.class) {
            int index = argList.indexOf("-" + annotation.value());
            value = String.valueOf(argList.get(index + 1));
        }
        return value;
    }
}
