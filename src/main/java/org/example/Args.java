package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {
    public static Map<Class<?>, OptionParser> PARSER_MAP = Map.of(
            boolean.class, new BooleanOptionParser(),
            int.class, new SingleValueOptionParser<>(Integer::valueOf),
            String.class, new SingleValueOptionParser<>(String::valueOf)
    );

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

    private static Object parseOption(Parameter parameter, List<String> args) {
        Option annotation = parameter.getAnnotation(Option.class);
        // 传入 true 参数，用构造函数创建对象
        return PARSER_MAP.get(parameter.getType()).parse(args, annotation);
    }

}
