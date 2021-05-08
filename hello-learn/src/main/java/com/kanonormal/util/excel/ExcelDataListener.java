package com.kanonormal.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * @title com.soranico.util.excel.ExcelDataListener
 * @description
 *        <pre>
 *          Excel每行数据监听
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/4
 *
 * </pre>
 */
public class ExcelDataListener extends AnalysisEventListener {

    /**
     * 当前块数据
     */
    private final List data;
    /**
     * 当前行
     */
    private static final AtomicInteger rowCount = new AtomicInteger(7);

    /**
     * 当前需要跳过的列
     */
    private static final AtomicInteger colCount = new AtomicInteger(60);

    /**
     * 当前跳过解析但需要解析的列
     */
    private static final AtomicInteger limitCount = new AtomicInteger(0);
    /**
     * 默认的数据缓存大小
     */
    private final int defaultSize;

    private static File file = new File("F:/result.txt");

    private static AtomicBoolean flag = new AtomicBoolean(true);
    private static PrintStream ps;

    static {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ps = new PrintStream(bos, true);
    }

    public ExcelDataListener(int defaultSize) {
        this.defaultSize = defaultSize;
        data=new ArrayList(defaultSize);
    }

    @Override
    public void invoke(Object currentRow, AnalysisContext context) {
        data.add(currentRow);
        if (data.size() >= defaultSize) {
            doCalculation(colCount.getAndAdd(56), limitCount.getAndIncrement());
            data.clear();
            flag.set(true);
        }

    }

    /**
     * 计算数据
     */
    private void doCalculation(int skip, int limit) {
        /**
         * 循环计算每行数据
         */
        for (Object currentData : data) {
            LinkedHashMap<Integer, String> currentRow = (LinkedHashMap) currentData;
            int colCurrent = rowCount.getAndIncrement();

            double skipSum = currentRow.values().stream().skip(skip).mapToDouble(Double::valueOf).sum();
            double adjustSum = currentRow.values().stream().skip(4).limit(limit * 56).mapToDouble(Double::valueOf).sum();

            System.setOut(ps);
            System.out.println("row = " + colCurrent + " ,  result  = " + (skipSum + adjustSum));

        }

    }

    /**
     * 防止丢失数据
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /**
         * 确保消息解析完成
         */
        doCalculation(colCount.get(), limitCount.get());
    }

}
