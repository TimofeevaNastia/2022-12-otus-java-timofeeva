package otus.ru.main;

import otus.ru.tests.RunTests;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        System.out.println("Запущены тесты");
        RunTests.run("otus.ru.tests.SuccessTest");
//        RunTests.run("otus.ru.tests.FailTest"); // проверка если тест fail
//        RunTests.run("otus.ru.tests.ExceptionTest"); // проверка, что все методы выполняются даже с исключением
    }
}


