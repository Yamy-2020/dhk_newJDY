package com.kym.ui;

import com.kym.ui.util.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * @author Msquirrel
 */
public class DateUtils {
    private DateUtils() {
        // 工具类, 禁止实例化
        throw new AssertionError();
    }

    /**
     * 通过指定的年份和月份获取当月有多少天.
     *
     * @param year  年.
     * @param month 月.
     * @return 天数.
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 获取指定年月的 1 号位于周几.
     *
     * @param year  年.
     * @param month 月.
     * @return 周.
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static SimpleDateFormat sf;

    /* 获取系统时间 格式为："yyyy/MM/dd " */
    public static String getCurrentDate1() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getDateToString8(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /* 获取系统时间 格式为："yyyy/MM/dd " */
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString2(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yy年MM月dd日");
        return sf.format(d);
    }

    public static String getDateToString3(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDateToString7(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sf.format(d);
    }

    public static String getDateToString4(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM/dd HH:mm");
        return sf.format(d);
    }

    public static String getDateToString6(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM/dd");
        return sf.format(d);
    }

    public static String getDateToString5(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDateToString9(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getDateToString10(long time) {
        String t = time + "";
        Date d;
        if (t.length() == 10) {
            log.e("秒时间戳");
            d = new Date(time * 1000);
        } else {
            log.e("毫秒时间戳");
            d = new Date(time);
        }
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static String getDateToString101(long time) {
        String t = time + "";
        Date d;
        if (t.length() == 10) {
            log.e("秒时间戳");
            d = new Date(time * 1000);
        } else {
            log.e("毫秒时间戳");
            d = new Date(time);
        }
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }


    public static long getStringToDate1(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}