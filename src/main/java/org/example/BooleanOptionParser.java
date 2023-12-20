package org.example;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> args, Option annotation) {
        int index = args.indexOf("-" + annotation.value());
        if (index+1 < args.size() &&
                !args.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(annotation.value());
        }
        return index != -1;
    }
}
