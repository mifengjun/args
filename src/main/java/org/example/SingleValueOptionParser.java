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
        if(index +1 < args.size() && !args.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(annotation.value());
        }
        return parseValue(args.get(index + 1));
    }

    public T parseValue(String value) {
        return valueParser.apply(value);
    }
}
