package otus.ru.tests;

import lombok.extern.slf4j.Slf4j;
import otus.ru.annotatiton.After;
import otus.ru.annotatiton.Before;
import otus.ru.annotatiton.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ExecutionTests {

    public static void run(String className) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();
        Constructor<?> constructor = clazz.getConstructor();
        runTests(methods, constructor);
    }

    private static void runTests(Method[] methods, Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] testsMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Test.class)
                && !Modifier.isPrivate(method.getModifiers())).toArray(Method[]::new);
        int countTest = testsMethods.length;
        AtomicInteger countSuccessTest = new AtomicInteger();
        AtomicInteger countFailTest = new AtomicInteger();
        boolean hasTests = countTest != 0;
        if (hasTests) {
            Method[] beforeMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Before.class)).toArray(Method[]::new);
            Method[] afterMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(After.class)).toArray(Method[]::new);
            checkPrivateBeforeAfter(beforeMethods);
            checkPrivateBeforeAfter(afterMethods);
            for (Method method : testsMethods) {
                Object object = constructor.newInstance();
                runBeforeOrAfter(beforeMethods, object);
                try {
                    method.invoke(object);
                    countSuccessTest.getAndIncrement();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    countFailTest.getAndIncrement();
                    e.printStackTrace();
                }
                runBeforeOrAfter(afterMethods, object);
            }
            log.info("Total tests: {}, Fail: {}, Success: {}", countTest, countFailTest.get(), countSuccessTest.get());
        }
    }


    private static void runBeforeOrAfter(Method[] methods, Object object) {
        Arrays.stream(methods).forEach(method -> {
            try {
                method.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static void checkPrivateBeforeAfter(Method[] methods) {
        Method[] privateMethods = Arrays.stream(methods).filter(method -> Modifier.isPrivate(method.getModifiers())).toArray(Method[]::new);
        if (privateMethods.length != 0) {
            throw new AssertionError("Methods " + Arrays.toString(privateMethods) + " with annotations before/after must not be private");
        }
    }
}
