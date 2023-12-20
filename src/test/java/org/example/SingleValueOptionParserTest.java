package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueOptionParserTest {
    @Test
    public void should_not_accept_extra_argument_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                new SingleValueOptionParser<>(Integer::parseInt).parse(Arrays.asList("-p", "8080", "123"), option("p")));
        assertEquals("p", e.getOption());
    }

    private Option option(String p) {
        return new Option() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return null;
            }

            @Override
            public String value() {
                return p;
            }
        };
    }
}
