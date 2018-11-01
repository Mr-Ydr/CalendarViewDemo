package com.drying.calendar.view;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/11 16:40
 * <p/>
 * Description:
 */
public class CalendarMo {
    private String year;
    private String month;
    private String day;
    private String week;
    private boolean isCheck = false;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
