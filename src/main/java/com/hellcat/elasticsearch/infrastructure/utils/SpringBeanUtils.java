package com.hellcat.elasticsearch.infrastructure.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component(SpringBeanUtils.BEAN_NAME)
public class SpringBeanUtils implements ApplicationContextAware {

    public static final String BEAN_NAME = "springBeanUtils";

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static String[] getBeanNames(Class<?> clazz) {
        return applicationContext.getBeanNamesForType(clazz);
    }
}
