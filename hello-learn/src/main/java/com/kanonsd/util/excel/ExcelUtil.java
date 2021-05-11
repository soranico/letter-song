package com.kanonsd.util.excel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * <pre>
 * @title com.soranico.excel.ExcelUtil
 * @description
 *        <pre>
 *          读取Excel TODO 需要封装
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/6/26
 *
 * </pre>
 */
@Slf4j
public class ExcelUtil {


    private static final String XLS_X = ".xlsx";
    private static final int DEFAULT_START_ROW = 6;


    public static void main(String[] args) {
        ExcelReaderBuilder readBuilder = getExcel("F:" + File.separator + "output" + XLS_X,56);
        readBuilder.sheet().headRowNumber(DEFAULT_START_ROW).doRead();
    }


    public static ExcelReaderBuilder getExcel(String filePath,int defaultSize) {
        File file = new File(filePath);
        ExcelReaderBuilder read = EasyExcel.read(file, getEventListener(defaultSize));
        return read;
    }

    private static AnalysisEventListener getEventListener(int defaultSize){
        return new ExcelDataListener(defaultSize);
    }


}

