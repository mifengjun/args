package org.example;

public class TooManyArgumentsException extends RuntimeException{

    private String option;

    public TooManyArgumentsException(String annotation) {
        this.option = annotation;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
