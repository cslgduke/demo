package com.example.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author i565244
 */
public class UserMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        String methodName = mi.getMethod().getName();// 方法名
        Object returnVal = null;

        if (methodName.startsWith("add")) {
            returnVal = beforeEnhance(mi);
        } else if (methodName.startsWith("delete")) {
            returnVal = afterThrowingEnhance(mi);
        } else {
            returnVal = mi.proceed();
        }
        return returnVal;
    }

    private Object beforeEnhance(MethodInvocation mi) throws Throwable {
        new ConsoloEnhancer().before(mi);
        Object returnVal = mi.proceed();
        return returnVal;
    }

    private Object afterThrowingEnhance(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable e) {
            new ConsoloEnhancer().afterThrowing(mi, e);
            throw e;
        }
    }

}
