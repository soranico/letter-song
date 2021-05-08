package com.kanonormal.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * <pre>
 * @title com.soranico.util.date.DateUtil
 * @description
 *        <pre>
 *          时间工具类，使用单例和策略模式
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/12
 *
 * </pre>
 */
public class DateUtil {


    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String HH_MM_SS = "HH:mm:ss";

    /**
     * 默认系统所在时区
     */
    private static final ZoneOffset DEFAULT_ZONE = ZoneOffset.of("GMT+8");

    //////////////// strategy ///////////////////


    private interface FormatterStrategy<T> {
        /**
         * 解析时间串为Date LocalDateTime LocalDate LocalTime
         *
         * @param date 时间串
         * @return
         */
        T parseDate(Object date);

        /**
         * 格式化Date LocalDateTime LocalDate LocalTime
         *
         * @param date 时间
         * @return 时间串
         */
        String formatDate(Object date);
    }
    /**
     * 匹配格式 yyyy-MM-dd HH:mm:ss
     */
    private static class YearMonthDayHourMinuterSecondFormatter implements FormatterStrategy<LocalDateTime> {
        private static final DateTimeFormatter YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

        private static final YearMonthDayHourMinuterSecondFormatter INSTANCE = new YearMonthDayHourMinuterSecondFormatter();

        public static YearMonthDayHourMinuterSecondFormatter getInstance() {
            return INSTANCE;
        }

        @Override
        public LocalDateTime parseDate(Object date) {
            if (date instanceof String) {
                return LocalDateTime.parse((String) date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER);
            }
            return ((Date) date).toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
        }

        @Override
        public String formatDate(Object date) {
            if (date instanceof Date) {
                return ((Date) date).toInstant().atZone(DEFAULT_ZONE).toLocalDateTime().format(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER);
            }
            return ((LocalDateTime) date).format(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER);
        }

    }

    /**
     * 匹配格式 yyyy-MM-dd
     */
    private static class YearMonthDayFormatter implements FormatterStrategy<LocalDate> {
        private static final DateTimeFormatter YEAR_MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);

        private static final YearMonthDayFormatter INSTANCE = new YearMonthDayFormatter();

        public static YearMonthDayFormatter getInstance() {
            return INSTANCE;
        }


        @Override
        public LocalDate parseDate(Object date) {
            if (date instanceof String) {
                return LocalDate.parse((String) date, YEAR_MONTH_DAY_FORMATTER);
            }
            return ((Date) date).toInstant().atZone(DEFAULT_ZONE).toLocalDate();
        }

        @Override
        public String formatDate(Object date) {
            if (date instanceof Date) {
                return ((Date) date).toInstant().atZone(DEFAULT_ZONE).toLocalDate().format(YEAR_MONTH_DAY_FORMATTER);
            }
            return ((LocalDate) date).format(YEAR_MONTH_DAY_FORMATTER);
        }

    }

    /**
     * 处理HH:mm:ss
     */
    private static class HourMinuteSecond implements FormatterStrategy<LocalTime> {
        private static final DateTimeFormatter HOUR_MINUTE_SECOND_FORMATTER = DateTimeFormatter.ofPattern(HH_MM_SS);

        private static final HourMinuteSecond INSTANCE = new HourMinuteSecond();

        public static HourMinuteSecond getInstance() {
            return INSTANCE;
        }

        @Override
        public LocalTime parseDate(Object date) {
            if (date instanceof String) {
                return LocalTime.parse((String) date, HOUR_MINUTE_SECOND_FORMATTER);
            }
            return ((Date) date).toInstant().atZone(DEFAULT_ZONE).toLocalTime();
        }

        @Override
        public String formatDate(Object date) {
            if (date instanceof Date) {
                return ((Date) date).toInstant().atZone(DEFAULT_ZONE).
                        toLocalDateTime().toLocalTime().format(HOUR_MINUTE_SECOND_FORMATTER);
            }
            return ((LocalTime) date).format(HOUR_MINUTE_SECOND_FORMATTER);
        }
    }

    /**
     * 根据格式选择不同实现策略
     *
     * @param pattern 格式匹配
     * @return 解析策略
     */
    private static FormatterStrategy formatterStrategyChooser(String pattern) {
        switch (pattern) {
            case YYYY_MM_DD:
                return YearMonthDayFormatter.getInstance();
            case HH_MM_SS:
                return HourMinuteSecond.getInstance();
            default:
                return YearMonthDayHourMinuterSecondFormatter.getInstance();
        }
    }

    /**
     * 将时间转为格式
     *
     * @param date    时间戳
     * @param pattern 转换格式
     * @return 时间串
     */
    private static String formatDateByPattern(Object date, String pattern) {
        FormatterStrategy FormatterStrategy = formatterStrategyChooser(pattern);
        return FormatterStrategy.formatDate(date);
    }

    /**
     * 将时间字符串转为Date
     *
     * @param date    时间串
     * @param pattern 转换格式
     * @return date
     * @throws UnsupportedOperationException 不支持转换
     */
    private static Object parseDateByPattern(String date, String pattern, boolean localDateFlag) {
        FormatterStrategy FormatterStrategy = formatterStrategyChooser(pattern);
        assert FormatterStrategy != null;
        Object parseDate = FormatterStrategy.parseDate(date);
        if (localDateFlag) {
            return parseDate;
        }
        if (parseDate instanceof LocalDateTime) {
            return new Date(((LocalDateTime) parseDate).toInstant(DEFAULT_ZONE).toEpochMilli());
        } else if (parseDate instanceof LocalDate) {
            return new Date(((LocalDate) parseDate).atStartOfDay().toInstant(DEFAULT_ZONE).toEpochMilli());
        } else if (parseDate instanceof LocalTime) {
            return new Date(LocalDateTime.of(LocalDate.now(), (LocalTime) parseDate).toInstant(DEFAULT_ZONE).toEpochMilli());
        } else {
            throw new UnsupportedOperationException("pattern must yyyy-MM-dd HH:mm:ss | yyyy-MM-dd | HH:mm:ss");
        }
    }
    /**
     * 转换Date为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateToYMDHMS(Date date) {
        return formatDateByPattern(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String formatDateToYMDHMS(LocalDateTime dateTime) {
        return formatDateByPattern(dateTime, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 转换 yyyy-MM-dd HH:mm:ss为Date
     *
     * @param date
     * @return
     */
    public static Date parseYMDHMSToDate(String date) {
        return (Date) parseDateByPattern(date, YYYY_MM_DD_HH_MM_SS, false);
    }


    /**
     * 转换 yyyy-MM-dd HH:mm:ss为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime parseYMDHMSToLocalDateTime(String date) {
        return (LocalDateTime) parseDateByPattern(date, YYYY_MM_DD_HH_MM_SS, true);
    }


    /**
     * 转换格式为 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDateToYMD(Date date) {
        return formatDateByPattern(date, YYYY_MM_DD);
    }

    public static String formatDateToYMD(LocalDate date) {
        return formatDateByPattern(date, YYYY_MM_DD);
    }

    /**
     * 转换 yyyy-MM-dd 为Date
     *
     * @param date
     * @return
     */
    public static Date parseYMDToDate(String date) {
        return (Date) parseDateByPattern(date, YYYY_MM_DD, false);
    }


    /**
     * 转换 yyyy-MM-dd 为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate parseYMDToLocalDate(String date) {
        return (LocalDate) parseDateByPattern(date, YYYY_MM_DD, true);
    }


    /**
     * 转换格式为 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDateToHMS(Date time) {
        return formatDateByPattern(time, HH_MM_SS);
    }

    public static String formatDateToHMS(LocalTime time) {
        return formatDateByPattern(time, HH_MM_SS);
    }


    /**
     * 转换 HH:mm:ss为Date 默认当天
     *
     * @param date
     * @return
     */
    public static Date parseHMSToDate(String date) {
        return (Date) parseDateByPattern(date, HH_MM_SS, false);
    }


    /**
     * 转换 yyyy-MM-dd HH:mm:ss为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalTime parseHMSToLocalTime(String date) {
        return (LocalTime) parseDateByPattern(date, HH_MM_SS, true);
    }

    /**
     * 获取所在月的第几天
     *
     * @param date 时间
     * @return 天数
     * @throws UnsupportedOperationException 当类型不支持抛出此异常
     */
    public static int dayOfMouth(Object date) {
        if (date instanceof Date) {
            return ((LocalDateTime) formatterStrategyChooser(YYYY_MM_DD_HH_MM_SS).parseDate(date)).getDayOfMonth();
        } else if (date instanceof LocalDateTime) {
            return ((LocalDateTime) date).getDayOfMonth();
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).getDayOfMonth();
        } else {
            throw new UnsupportedOperationException("date must Date | LocalDateTime | LocalDate ");
        }
    }


    /**
     * 获取时间所在年
     *
     * @param date 时间
     * @return 年
     * @throws UnsupportedOperationException 当类型不支持抛出此异常
     */
    public static int getYear(Object date) {
        if (date instanceof Date) {
            return ((LocalDateTime) formatterStrategyChooser(YYYY_MM_DD_HH_MM_SS).parseDate(date)).getYear();
        } else if (date instanceof LocalDateTime) {
            return ((LocalDateTime) date).getYear();
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).getYear();
        } else {
            throw new UnsupportedOperationException("date must Date | LocalDateTime | LocalDate ");
        }
    }

    /**
     * 相同年月日
     *
     * @param date01 日期1
     * @param date02 日期2
     * @return 相同 true
     */
    public static boolean sameYearMouthDay(Object date01, Object date02) {
        LocalDate localDate01 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date01);
        LocalDate localDate02 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date02);
        return localDate01.isEqual(localDate02);
    }

    /**
     * 相同年月
     *
     * @param date01
     * @param date02
     * @return
     */
    public static boolean sameYearMouth(Object date01, Object date02) {
        LocalDate localDate01 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date01);
        LocalDate localDate02 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date02);
        return localDate01.getYear() == localDate02.getYear() && localDate01.getMonthValue() == localDate01.getMonthValue();
    }

    /**
     * 同年同季度
     *
     * @param date01
     * @param date02
     * @return
     */
    public static boolean sameYearSeason(Object date01, Object date02) {
        LocalDate localDate01 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date01);
        LocalDate localDate02 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date02);
        return localDate01.getYear() == localDate02.getYear() && (localDate01.getMonthValue() - 1) / 3 == (localDate01.getMonthValue() - 1) / 3;
    }


    /**
     * 同一年
     *
     * @param date01
     * @param date02
     * @return
     */
    public static boolean sameYear(Object date01, Object date02) {
        LocalDate localDate01 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date01);
        LocalDate localDate02 = (LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date02);
        return localDate01.getYear() == localDate02.getYear();
    }

    /**
     * 将时间秒格式化
     *
     * @param timestamp
     * @return
     */
    public static String formatTimestampSecondToYMDHMS(String timestamp) {
        return formatDateToYMDHMS(new Date(Long.parseLong(timestamp) * 1000));
    }

    /**
     * 时间戳
     *
     * @param timestamp
     * @return
     */
    public static String formatTimestampToYMDHMS(String timestamp) {
        return formatDateToYMDHMS(new Date(Long.parseLong(timestamp)));
    }

    /**
     * 时间上个月的第一天或者最后一天
     *
     * @param date      时间
     * @param firstFlag 第一天标记
     * @return
     */
    public static Date firstOrLastDayInLastMouth(Object date, boolean firstDayFlag) {
        LocalDate localDate;
        if (date instanceof Date) {
            localDate = ((LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date)).plusMonths(-1);
        } else if (date instanceof LocalDateTime) {
            localDate = ((LocalDateTime) date).plusMonths(-1).toLocalDate();
        } else if (date instanceof LocalDate) {
            localDate = ((LocalDate) date).plusMonths(-1);
        } else {
            throw new UnsupportedOperationException("date must Date | LocalDateTime | LocalDate ");
        }
        return firstDayFlag ? new Date(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(DEFAULT_ZONE).toEpochMilli())
                : new Date(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().toInstant(DEFAULT_ZONE).toEpochMilli());
    }

    /**
     * 本月第一天或者最后一天
     *
     * @param date
     * @param firstDayFlag
     * @return
     */
    public static Date firstOrLastDayInThisMouth(Object date, boolean firstDayFlag) {
        LocalDate localDate;
        if (date instanceof Date) {
            localDate = ((LocalDate) formatterStrategyChooser(YYYY_MM_DD).parseDate(date));
        } else if (date instanceof LocalDateTime) {
            localDate = ((LocalDateTime) date).toLocalDate();
        } else if (date instanceof LocalDate) {
            localDate = (LocalDate) date;
        } else {
            throw new UnsupportedOperationException("date must Date | LocalDateTime | LocalDate ");
        }
        return firstDayFlag ? new Date(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(DEFAULT_ZONE).toEpochMilli())
                : new Date(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().toInstant(DEFAULT_ZONE).toEpochMilli());
    }



}
