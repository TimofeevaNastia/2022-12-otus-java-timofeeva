package ru.otus.aop;

import ru.otus.aop.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

class ProxyTest {

    private ProxyTest() {
    }

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new TestHandler(new TestLogging());
        return (TestLoggingInterface) java.lang.reflect.Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    record TestHandler(TestLoggingInterface testLogging) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (testLogging.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                System.out.println("executed method: " + method.getName() + ", param: "
                        + Arrays.toString(args));
            }
            return method.invoke(testLogging, args);
        }
    }
}
