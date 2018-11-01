package com.drying.calendar.view;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/12 21:16
 * <p/>
 * Description:单元测试
 */
public class Test {
    public static void main(String[] args) {
        long   time = System.currentTimeMillis();
        String a    = DateUtil.getTime(DateUtil.Format.DATE, time);
        String week = DateUtil.getWeek(time);
        System.out.println("时间：" + a + "，，星期：" + week);
    }
}
