package ru.otus.aop;

import ru.otus.aop.annotation.Log;
import ru.otus.aop.type.TestType;

public class TestLogging implements TestLoggingInterface {

    @Log
    public void calculation(int param) {
//        System.out.println("Param: " + param);
    }

    @Log
    public void calculation(String param1, int param2) {
//        System.out.println("Param 1: " + param1);
//        System.out.println("Param 2: " + param2);
    }

    @Log
    public void calculation(TestType value) {
//        System.out.println("TestType, value: " + value);
    }
}
