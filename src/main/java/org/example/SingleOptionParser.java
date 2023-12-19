package org.example;

import java.util.List;
import java.util.function.Function;

public class SingleOptionParser<T> implements OptionParser {
    private final Function<String, T> valueParser;

    public SingleOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public Object parse(List<String> args, Option annotation) {
        int index = args.indexOf("-" + annotation.value());
        String value = args.get(index + 1);
        return parseValue(value);
    }

    public T parseValue(String value) {
        return valueParser.apply(value);
    }
}
