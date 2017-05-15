package testGuava;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ruohanpan on 2017/5/15.
 */
public class ReflectionTest {
    public static void main(String[] args) {
        InvocationHandler invocationHandler = new MyInvocationHandler();
        //guava动态代理实现
        IFoo foo = Reflection.newProxy(IFoo.class, invocationHandler);
        foo.doSomething();
        //jdk动态代理实现
        IFoo jdkFoo = (IFoo) Proxy.newProxyInstance(
                IFoo.class.getClassLoader(),
                new Class<?>[]{IFoo.class},
                invocationHandler);
        jdkFoo.doSomething();
        //guava Invokable是对Java.lang.reflect.Method和Java.lang.reflect.Constructor的流式包装
    }

    public static class MyInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxy .....");
            return null;
        }
    }

    public static interface IFoo {
        void doSomething();
    }

}
