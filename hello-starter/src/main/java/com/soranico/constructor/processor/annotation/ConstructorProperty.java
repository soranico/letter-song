package com.soranico.constructor.processor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * @title com.soranico.constructor.processor.annotation.ConstructorProperty
 * @description
 *        <pre>
 *          标记需要处理的构造方法
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/6/21
 *
 * </pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorProperty {
    /**
     * 配置文件中的key
     * @return
     */
    String name();

    /**
     * 构造方法的参数下标
     * @return
     */
    int order() default 0;

}
