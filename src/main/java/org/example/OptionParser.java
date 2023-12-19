package org.example;

import java.util.List;

public interface OptionParser {
    Object parse(List<String> args, Option annotation);
}
