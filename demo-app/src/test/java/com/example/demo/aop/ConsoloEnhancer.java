package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

/**
 * @author i565244
 */
@Slf4j
public class ConsoloEnhancer {

    public void before(MethodInvocation mi) {
        if (mi.getThis().getClass() == UserDaoImpl.class) {         //对方法入参进行修改
            Object[] args = mi.getArguments();
            String enhancedAgr ="user-" +(String) args[0]  ;
            args[0] = enhancedAgr;
        }
        log.info("enhanced params:{}",Arrays.toString(mi.getArguments()));
    }

    public void afterThrowing(MethodInvocation mi, Throwable e) {
        log.info("ivoke method:{} of {} with params:{} occur exception",mi.getMethod().getName(),mi.getThis(),Arrays.toString(mi.getArguments()),e);
    }


}
