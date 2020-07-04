package com.soranico.constructor.processor;


import com.soranico.constructor.processor.annotation.ConstructorProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 * @title com.soranico.constructor.processor.ConstructorPropertyProcessor
 * @description
 *        <pre>
 *          处理被ConstructorProperty标记的BeanDefinition
 *          模拟手动往构造方法注入配置文件的参数
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/6/21
 *
 * </pre>
 */
public final class ConstructorPropertyProcessorApplicationListener implements BeanFactoryPostProcessor, ApplicationListener {

    private static final Logger log = LoggerFactory.getLogger(ConstructorPropertyProcessorApplicationListener.class);
    /**
     * 标记配置文件初始化状态
     */
    private static final AtomicBoolean initFlag = new AtomicBoolean(true);
    /**
     * 配置文件,暂时默认读取application.properties
     */
    private static Properties properties;
    /**
     * 需要排除解析的package
     */
    private static List<String> excludeList = new ArrayList<>();

    private static final String DEFAULT_PROPERTIES = "properties/application.properties";
    private static final String DEFAULT_EXCLUDE_KEY = "ConstructorProperty.exclude";

    static {
        excludeList.add("org.springframework");
    }

    /**
     * 监听感兴趣的事件
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            initProperties();
            /** 加载默认配置文件 */
            ((ApplicationStartingEvent) event).getSpringApplication().setDefaultProperties(properties);
        } else if (event instanceof ApplicationPreparedEvent) {
            /** 添加处理@QProperty的后置处理器 */
            ((ApplicationPreparedEvent) event).getApplicationContext().addBeanFactoryPostProcessor(this);
        }

    }

    /**
     * 处理被@QProperty标记的构造方法
     *
     * @param factory 工厂
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

        String[] beanDefinitionNames = factory.getBeanDefinitionNames();
        ClassLoader loader = factory.getBeanClassLoader();
        Class clazz = null;
        String className;
        next:
        for (String beanDefinitionName : beanDefinitionNames) {
            /**
             * 排除无需解析的BeanDefinition
             */
            for (String exclude : excludeList) {
                if (beanDefinitionName.contains(exclude)) continue next;
            }
            BeanDefinition beanDefinition = factory.getBeanDefinition(beanDefinitionName);
            try {
                if ((className = beanDefinition.getBeanClassName()) != null)
                    clazz = loader.loadClass(className);
            } catch (Exception e) {
                log.error("Loader Class Failed , Class = " + beanDefinition.getBeanClassName());
                e.printStackTrace();
            }
            if (clazz == null) {
                continue;
            }
            Constructor[] constructors = clazz.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                Parameter[] parameters = constructor.getParameters();
                for (Parameter parameter : parameters) {
                    ConstructorProperty qProperty = parameter.getAnnotation(ConstructorProperty.class);
                    if (qProperty == null) {
                        continue;
                    }
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(qProperty.order(), properties.get(qProperty.name()));
                }
            }
            clazz = null;
        }

    }


    /**
     * 初始化配置文件
     */
    private static void initProperties() {
        /**
         * CAS标记初始化状态
         */
        if (initFlag.compareAndSet(true,false)) {
            properties = new Properties();
            String propertiesPath = System.getProperty("propertiesPath");
            if (propertiesPath == null || propertiesPath.isEmpty()) {
                propertiesPath = DEFAULT_PROPERTIES;
            }
            try (InputStream is = ConstructorPropertyProcessorApplicationListener.class.getClassLoader().getResourceAsStream(propertiesPath)) {
                properties.load(is);
                initExcludePackage();
            } catch (Exception e) {
                log.error("failed to parse properties file , properties = " + propertiesPath);
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化配置文件中的需要排除的类
     */
    private static void initExcludePackage() {
        String excludeArray = (String) properties.get(DEFAULT_EXCLUDE_KEY);
        if (Objects.isNull(excludeArray) || excludeArray.isEmpty()) {
            return;
        }
        excludeList.addAll(Arrays.asList(excludeArray.split(",")));
    }

}
