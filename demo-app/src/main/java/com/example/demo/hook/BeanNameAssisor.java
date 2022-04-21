package com.example.demo.hook;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author i565244
 */
@Component
public class BeanNameAssisor implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBeanDefinition(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBeanDefinition(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }
}
