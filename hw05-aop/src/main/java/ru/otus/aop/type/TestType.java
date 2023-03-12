package ru.otus.aop.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestType {
    private int value;

    public TestType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestType{" +
                "value=" + value +
                '}';
    }
}
