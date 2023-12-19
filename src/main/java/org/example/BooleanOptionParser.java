package org.example;

import java.util.List;

class BooleanOptionParser implements OptionParser {
    @Override
    public Object parse(List<String> args, Option annotation) {
        return args.contains("-" + annotation.value());
    }
}
