package ru.otus;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.System.out;

public class HelloOtus {

    public static void main(String[] args) {
        out.println("Text: " + getText(null));

    }

    public static String getText(String value) {
        return firstNonNull(value, "Value is null");
    }
}
