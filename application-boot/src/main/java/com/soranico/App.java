package com.soranico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <pre>
 * @title com.soranico.App
 * @description
 *        <pre>
 *          启动类
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/6/21
 *
 * </pre>
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(App.class);
        application.run(args);
    }
}

