package com.soranico.service;

import com.soranico.annotation.QProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * @title com.soranico.service.QPropertyService
 * @description
 *        <pre>
 *          测试@QProperty
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/6/21
 *
 * </pre>
 */
@Service
@Slf4j
public class QPropertyService {

    /**
     * 这个参数要从配置文件中读取
     */
    public QPropertyService(@QProperty(name = "QProperty.key") String key) {
        log.info("读取到配置文件值 QProperty.key = {}", key);
    }

}
