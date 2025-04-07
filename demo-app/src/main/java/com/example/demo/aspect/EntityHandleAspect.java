package com.example.demo.aspect;

import com.example.demo.annotation.PreHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author i565244
 */
@Aspect
@Component
@Slf4j
public class EntityHandleAspect  {

    @Autowired
    private List<OdataHandler> preHandlers;

    @Before("@annotation(com.example.demo.annotation.EntityHandlePoint)")
    public void entityPreHandle(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        log.info("execute hookRespAreaActions....");
//        log.info("execute checkBoModifierControl....");
        preHandlers.forEach( handler -> {
            handler.handle();
        });
    }


    @After("@annotation(com.example.demo.annotation.EntityHandlePoint)")
    public void entityPostHandle(JoinPoint joinPoint) {
        log.info("execute afterPersistBo....");
    }


    public static void main(String[] args) {
                Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.example"))
                        .setScanners(new TypeAnnotationsScanner(),new SubTypesScanner()).useParallelExecutor());
        var handlerClass =  reflections.getTypesAnnotatedWith(PreHandler.class);
        handlerClass.forEach(hc -> {
            log.info(hc.getCanonicalName());
        });
    }

}
