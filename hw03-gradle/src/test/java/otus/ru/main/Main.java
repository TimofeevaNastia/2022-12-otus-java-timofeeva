package otus.ru.main;

import otus.ru.tests.ExecutionTests;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        System.out.println("Запущены тесты");
        ExecutionTests.run("otus.ru.tests.SuccessTest");
//      ExecutionTests.run("otus.ru.tests.FailTest"); // проверка если тест fail
//      ExecutionTests.run("otus.ru.tests.ExceptionTest"); // проверка, что все методы выполняются даже с исключением
    }
}


