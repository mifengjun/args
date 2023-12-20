package org.example;

import java.util.List;

public interface OptionParser<T> {
    T parse(List<String> args, Option annotation);
}
