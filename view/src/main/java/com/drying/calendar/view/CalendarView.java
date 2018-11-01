package com.drying.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/11 16:10
 * <p/>
 * Description:自定义日历view
 */
public class CalendarView extends View {
    private OnClickListener onClickListener;
    private Parameter       parameter;
    private CanvasUtis      canvasUtis;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parameter = new Parameter();
        canvasUtis = new CanvasUtis(context, parameter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parameter.setViewWidth(getMeasuredWidth());
        parameter.setViewHeight(getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasUtis.initText(canvas);
        canvasUtis.initTitle(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                parameter.setDownX(x);
                parameter.setDownX1(x);
                /**判断是否在允许滑动位置*/
                parameter.setNotMove(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (parameter.isNotMove()) {
                    //计算偏移量
                    parameter.setOffset(x, true);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                float y = event.getY();
                /**判断是滑动还是点击*/
                if (parameter.getDownX1() - x > -20 && parameter.getDownX1() - x < 20) {
                    //日期选中事件
                    if (y >= parameter.getViewHeight() / parameter.getHeightTotal() * 2) {
                        if (parameter.getDataIsNull())
                            break;
                        CalendarMo mo = parameter.toCheckUpData(x);
                        invalidate();
                        if (onClickListener != null)
                            onClickListener.onClickListener(this, mo.getYear(), mo.getMonth(), mo.getDay());
                        break;
                    }

                    if (parameter.isLeftClick() && canvasUtis.getIsLeftClick(x, y)) {
                        parameter.toLeftMove(this);
                        parameter.setRightClick(true);
                        break;
                    }
                    if (parameter.isRightClick() && canvasUtis.getIsRightClick(x, y)) {
                        parameter.setRightClick(parameter.toRightMove(this));

                        break;
                    }
                } else if (parameter.isNotMove()) {
                    //计算偏移量
                    parameter.setOffset(x, false);
                    parameter.setDownX(x);

                    invalidate();
                    /**判断数据是否要加载完，如是则增加数据*/
                    parameter.checkAddData();
                }
                if (parameter.getCurX() == 0) {
                    parameter.setRightClick(false);
                } else {
                    parameter.setRightClick(true);
                }

                break;
        }

        return true;
    }

    /**
     * 设置点击事件回调
     *
     * @param onClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        /** 选中日期回调 */
        void onClickListener(View v, String year, String month, String day);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////设置属性//////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        canvasUtis.setTextSize(textSize);
        invalidate();
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        canvasUtis.setTextColor(textColor);
        invalidate();
    }

    /**
     * 设置标题文字大小
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(int titleTextSize) {
        canvasUtis.setTitleTextSize(titleTextSize);
        invalidate();
    }

    /**
     * 设置标题文字颜色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        canvasUtis.setTitleTextColor(titleTextColor);
        invalidate();
    }

    /**
     * 设置被选中的文字颜色
     *
     * @param checkTextColor
     */
    public void setCheckTextColor(int checkTextColor) {
        canvasUtis.setCheckTextColor(checkTextColor);
        invalidate();
    }

    /**
     * 设置被选中的背景颜色
     *
     * @param checkTextBgColor
     */
    public void setCheckTextBgColor(int checkTextBgColor) {
        canvasUtis.setCheckTextBgColor(checkTextBgColor);
        invalidate();
    }

    /**
     * 设置左面按钮图片
     *
     * @param leftImg
     */
    public void setLeftImg(int leftImg) {
        canvasUtis.setLeftImg(leftImg);
        invalidate();
    }

    /**
     * 设置右面按钮图片
     *
     * @param rightImg
     */
    public void setRightImg(int rightImg) {
        canvasUtis.setRightImg(rightImg);
        invalidate();
    }

    /**
     * 设置右面不可点击按钮图片
     *
     * @param rightHintImg
     */
    public void setRightHintImg(int rightHintImg) {
        canvasUtis.setRightHintImg(rightHintImg);
        invalidate();
    }
}
