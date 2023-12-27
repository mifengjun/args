package org.example;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {
    // Sad Path:
    // TODO     - Bool: -l f / -l 0

    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                new BooleanOptionParser().parse(Arrays.asList("-l", "t"), option("l")));
        assertEquals("l", e.getOption());
    }
    @Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                new BooleanOptionParser().parse(Arrays.asList("-l", "t","123"), option("l")));
        assertEquals("l", e.getOption());
    }

    @Test
    public void should_default_to_false_if_option_not_present() {
        assertFalse(new BooleanOptionParser().parse(List.of(), option("l")));
    }

    private Option option(String value) {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
