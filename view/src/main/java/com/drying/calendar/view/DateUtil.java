package com.drying.calendar.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/12 20:57
 * <p/>
 * Description:日期工具类
 */
public class DateUtil {
    public enum Format {
        /** 日期 + 时间类型格式，到秒 */
        SECOND("yyyy-MM-dd HH:mm:ss"),
        /** 日期类型格式，到日 */
        DATE("yyyy-MM-dd"),
        /** 日期类型格式，到月 */
        MONTH_CHINA("yyyy年MM月"),
        /** 天 */
        YEAR("yyyy"),
        /** 天 */
        MONTH("MM"),
        /** 天 */
        DAY("dd");
        // 格式化格式
        private String value;

        Format(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static String getTime(Format format, long date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.value, Locale.CHINA);
        String           time             = simpleDateFormat.format(date);
        return time;
    }

    public static String getWeek(long time) {
        Date     data     = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case Calendar.MONDAY:
                return "一";
            case Calendar.TUESDAY:
                return "二";
            case Calendar.WEDNESDAY:
                return "三";
            case Calendar.THURSDAY:
                return "四";
            case Calendar.FRIDAY:
                return "五";
            case Calendar.SATURDAY:
                return "六";
            case Calendar.SUNDAY:
                return "日";
        }
        return null;
    }
}
