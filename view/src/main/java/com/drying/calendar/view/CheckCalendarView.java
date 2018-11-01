package com.drying.calendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/30 15:36
 * <p/>
 * Description:自定义周显示日历控件
 */
public class CheckCalendarView extends CalendarView {
    public CheckCalendarView(Context context) {
        this(context, null);
    }

    public CheckCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray styledAttrs      = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        Integer    textSize         = styledAttrs.getInteger(R.styleable.CalendarView_textSize, 30);
        Integer    textColor        = styledAttrs.getColor(R.styleable.CalendarView_textColor, Color.WHITE);
        Integer    titleTextSize    = styledAttrs.getInteger(R.styleable.CalendarView_titleTextSize, 40);
        Integer    titleTextColor   = styledAttrs.getColor(R.styleable.CalendarView_titleTextColor, Color.WHITE);
        Integer    checkTextColor   = styledAttrs.getColor(R.styleable.CalendarView_checkTextColor, Color.BLUE);
        Integer    checkTextBgColor = styledAttrs.getColor(R.styleable.CalendarView_checkTextBgColor, Color.WHITE);
        Integer    leftImg          = styledAttrs.getResourceId(R.styleable.CalendarView_leftImg, R.drawable.calendar_left_true);
        Integer    rightImg         = styledAttrs.getResourceId(R.styleable.CalendarView_rightImg, R.drawable.calendar_right_true);
        Integer    rightHintImg     = styledAttrs.getResourceId(R.styleable.CalendarView_rightHintImg, /*R.drawable.calendar_right_false*/0);
        setCheckTextBgColor(checkTextBgColor);
        setCheckTextColor(checkTextColor);
        setLeftImg(leftImg);
        setRightImg(rightImg);
        setRightHintImg(rightHintImg);
        setTextColor(textColor);
        setTextSize(textSize);
        setTitleTextColor(titleTextColor);
        setTitleTextSize(titleTextSize);
    }

    @Override
    public void setTextSize(int textSize) {
        super.setTextSize(textSize);
    }

    @Override
    public void setTextColor(int textColor) {
        super.setTextColor(textColor);
    }

    @Override
    public void setTitleTextSize(int titleTextSize) {
        super.setTitleTextSize(titleTextSize);
    }

    @Override
    public void setTitleTextColor(int titleTextColor) {
        super.setTitleTextColor(titleTextColor);
    }

    @Override
    public void setCheckTextColor(int checkTextColor) {
        super.setCheckTextColor(checkTextColor);
    }

    @Override
    public void setCheckTextBgColor(int checkTextBgColor) {
        super.setCheckTextBgColor(checkTextBgColor);
    }

    @Override
    public void setLeftImg(int leftImg) {
        super.setLeftImg(leftImg);
    }

    @Override
    public void setRightImg(int rightImg) {
        super.setRightImg(rightImg);
    }

    @Override
    public void setRightHintImg(int rightHintImg) {
        super.setRightHintImg(rightHintImg);
    }

    public void setOnCheckClickListener(OnCheckClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
    }

    public static abstract class OnCheckClickListener implements OnClickListener {
        public abstract void onCheckClickListeners(View v, String year, String month, String day);

        @Override
        public void onClickListener(View v, String year, String month, String day) {
            onCheckClickListeners(v, year, month, day);
        }
    }
}
