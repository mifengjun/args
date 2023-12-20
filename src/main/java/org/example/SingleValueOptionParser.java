package org.example;

import java.util.List;
import java.util.function.Function;

public class SingleValueOptionParser<T> implements OptionParser<T> {
    private final Function<String, T> valueParser;

    public SingleValueOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> args, Option annotation) {
        int index = args.indexOf("-" + annotation.value());
        String value = args.get(index + 1);
        return parseValue(value);
    }

    public T parseValue(String value) {
        return valueParser.apply(value);
    }
}
