package ru.otus.aop;

import ru.otus.aop.type.TestType;

public interface TestLoggingInterface {

    void calculation(int param);

    void calculation(String param1, int param2);

    void calculation(TestType value);
}
