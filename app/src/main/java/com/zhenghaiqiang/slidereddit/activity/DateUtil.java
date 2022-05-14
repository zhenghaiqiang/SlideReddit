package com.zhenghaiqiang.slidereddit.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String strToTime(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String time = "";
        try {
            date = sdf.parse(strTime);
            long sometime = date.getTime();
            time = getTimeForShow(sometime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public static long strToLong(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        long time = System.currentTimeMillis();
        try {
            date = sdf.parse(strTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long strToLong2(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        long time = System.currentTimeMillis();
        try {
            date = sdf.parse(strTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long strToLong3(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        long time = System.currentTimeMillis();
        try {
            date = sdf.parse(strTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String strToDateTime(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat titleSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = "";
        try {
            date = sdf.parse(strTime);
            time = titleSdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String yyMMddToMMdd(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat titleSdf = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        String time = "";
        try {
            date = sdf.parse(strTime);
            time = titleSdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getStrTime(long time, String style) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat(style);

        re_StrTime = sdf.format(new Date(time));

        return re_StrTime;

    }

    public static String getTimeForShow(long receiveTime) {
        String strDate = "";
        //今年
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);

        //传进来的日期的年份
        now.setTimeInMillis(receiveTime);
        Date receiveDate = now.getTime();
        int receiveYear = now.get(Calendar.YEAR);

        /**
         * 判断是否是同一年
         */
        if (nowYear == receiveYear) {
            Calendar today = Calendar.getInstance();
            //获取今天过的毫秒数
            long todayMs = 1000 * (today.get(Calendar.HOUR_OF_DAY) * 3600
                    + today.get(Calendar.MINUTE) * 60 + today.get(Calendar.SECOND));
            //获取从1970到此刻的毫秒数
            long todayMsTotal = today.getTimeInMillis();

            if (todayMsTotal - receiveTime < todayMs) {
                //今天，使用 时:分
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                strDate = sdf.format(receiveDate);
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000)) {
                //使用 昨天 时:分
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String hourMinute = sdf.format(receiveDate);
                strDate = "昨天 " + hourMinute;
            } else if (todayMsTotal - receiveTime < (todayMs + 24 * 3600 * 1000 * 2)) {
                //前天
                strDate = "前天";
            } else {
                //今年的更早，使用月-日
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                strDate = sdf.format(receiveDate);
            }
        } else {
            //往年，使用年-月-日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strDate = sdf.format(receiveDate);
        }
        return strDate;
    }

    public static String getTimeDisplay(long getTime) {

        final long currTime = System.currentTimeMillis();
        final Date formatSysDate = new Date(currTime);

        // 判断当前总天数
        final int sysMonth = formatSysDate.getMonth() + 1;
        final int sysYear = formatSysDate.getYear();

        // 计算服务器返回时间与当前时间差值
        final long seconds = (currTime - getTime) / 1000;
        final long minute = seconds / 60;
        final long hours = minute / 60;
        final long day = hours / 24;
        final long month = day / calculationDaysOfMonth(sysYear, sysMonth);
        final long year = month / 12;

        if (year > 0) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm");
            return simpleDateFormat.format(new Date(getTime));
        } else if (month > 0 || day > 30) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "MM-dd HH:mm");
            return simpleDateFormat.format(new Date(getTime));
        } else if (day > 0) {
            return day + "天前";
        } else if (hours > 0) {
            return hours + "小时前";
        } else if (minute > 9) {
            return minute + "分钟前";
        } else {
            return "刚刚"; //都换成分钟前
        }
    }

    /**
     * 计算月数
     *
     * @return
     */
    private static int calculationDaysOfMonth(int year, int month) {
        int day = 0;
        switch (month) {
            // 31天
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            // 30天
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            // 计算2月天数
            case 2:
                day = year % 100 == 0 ? year % 400 == 0 ? 29 : 28
                        : year % 4 == 0 ? 29 : 28;
                break;
        }

        return day;
    }

}
