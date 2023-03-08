package ru.otus.aop;

import ru.otus.aop.type.TestType;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLoggingInterface = ProxyTest.createTestLogging();
        testLoggingInterface.calculation(new TestType(25));
        testLoggingInterface.calculation(6);
        testLoggingInterface.calculation(7);
        testLoggingInterface.calculation("6", 7);
    }
}
