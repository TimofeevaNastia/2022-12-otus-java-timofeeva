package ru.otus.aop;

import ru.otus.aop.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ProxyTest {

    private ProxyTest() {
    }

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new TestHandler(new TestLogging());
        return (TestLoggingInterface) java.lang.reflect.Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }


    static class TestHandler implements InvocationHandler {
        private final TestLoggingInterface testLogging;
        private final Map<Method, Boolean> calledMethods = new HashMap<>();

        TestHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!calledMethods.containsKey(method)) {
                calledMethods.put(method, hasAnnotation(method, Log.class));
            }
            printLog(method, args);
            return method.invoke(testLogging, args);
        }

        private void printLog(Method method, Object[] args) {
            if (calledMethods.get(method)) {
                System.out.println("executed method: " + method.getName() + ", param: "
                        + Arrays.toString(args));
            }
        }

        private boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) throws NoSuchMethodException {
            return testLogging.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(annotation);
        }
    }
}
