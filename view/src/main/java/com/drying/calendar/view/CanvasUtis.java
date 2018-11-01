package com.drying.calendar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/30 16:31
 * <p/>
 * Description:画图类
 */
public class CanvasUtis {
    private Parameter parameter;
    private Context   context;
    /** 标题长度 */
    private float     titleWidth;
    /** 选择画笔 */
    private Paint     paintTitle;
    /** 写字画笔 */
    private Paint     paintText;
    /** 选中画圈画笔 */
    private Paint     paintCircle;
    ////////////////////////////
    ////////////属性///////////
    ///////////////////////////
    /** 文字大小 */
    private int textSize = 30;
    /** 文字颜色 */
    private int    textColor;
    /** 标题文字大小 */
    private int    titleTextSize;
    /** 标题文字颜色 */
    private int    titleTextColor;
    /** 选中文字颜色 */
    private int    checkTextColor;
    /** 选中文字背景颜色 */
    private int    checkTextBgColor;

    ////////////////////////////
    /////////左右按钮//////////
    ///////////////////////////
    /** 左面选中日期按钮 */
    private int    leftImg;
    /** 右面选中日期按钮 */
    private int    rightImg;
    /** 右面不可点击选中日期按钮 */
    private int    rightHintImg;
    /** 左面图片按钮 */
    private Bitmap leftBitmap;
    /** 右面图片按钮 */
    private Bitmap rightBitmap;

    public CanvasUtis(Context context, Parameter parameter) {
        this.context = context;
        this.parameter = parameter;
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);
        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(checkTextBgColor);
        paintCircle.setStyle(Paint.Style.FILL);
        paintTitle = new Paint();
        paintTitle.setAntiAlias(true);
        paintTitle.setColor(titleTextColor);
        paintTitle.setTextSize(titleTextSize);
    }

    public void initTitle(Canvas canvas) {
        /**通过获取屏幕中间位置的日更新title上年月份*/
        CalendarMo mo    = parameter.getIndexData(4);
        String     title = mo.getYear() + "年" + mo.getMonth() + "月";
        titleWidth = paintText.measureText(title, 0, title.length());
        if (parameter.isLeftClick()) {
            leftBitmap = getBitmap(leftImg);
        } else {
            leftBitmap = null;
        }
        if (parameter.isRightClick()) {
            rightBitmap = getBitmap(rightImg);
        } else {
            if (rightHintImg == 0) {
                rightBitmap = null;
            } else {
                rightBitmap = getBitmap(rightHintImg);
            }
        }
        canvas.drawText(title, parameter.getTitleX(titleWidth), parameter.getTitleY(leftBitmap.getHeight()), paintTitle);
        if (getLeftStartX() != -1) {
            canvas.drawBitmap(leftBitmap, getLeftStartX(), parameter.getImgStartY(), paintTitle);
        }
        if (getRightStartX() != -1) {
            canvas.drawBitmap(rightBitmap, getRightStartX(), parameter.getImgStartY(), paintTitle);
        }
    }

    public void initText(Canvas canvas) {
        for (int i = 0; i < parameter.getDataSize(); i++) {
            String day       = parameter.getData(i).getDay();
            String week      = parameter.getData(i).getWeek();
            float  dayWidth  = paintText.measureText(day, 0, day.length());
            float  weekWidth = paintText.measureText(week, 0, week.length());
            /**获取屏幕最右面的item下标*/
            int rightIndex = parameter.getNewIndexPosition(7);
            /**获取屏幕最左面的item下标*/
            int leftIndex = parameter.getNewIndexPosition(1);
            /**获取屏幕最左面前一个的item下标*/
            int yuIndex = parameter.getNewIndexPosition(0);
            //设这一屏的第一个和最后一个特殊颜色
            if (i == rightIndex || i == leftIndex || i == yuIndex) {
                paintText.setColor(context.getResources().getColor(R.color.textLeftColor));
            } else {
                paintText.setColor(textColor);
            }
            //设置星期文字
            canvas.drawText(week, parameter.getTextX(i, weekWidth), parameter.getTextY(3), paintText);
            //判断是否选择
            if (parameter.getData(i).isCheck()) {
                canvas.drawCircle(parameter.getCircleX(i), parameter.getCircleY(textSize), parameter.getCircleR(), paintCircle);
                paintText.setColor(checkTextColor);
                canvas.drawText(day, parameter.getTextX(i, dayWidth), parameter.getTextY(4), paintText);
            } else {
                if (i == rightIndex || i == leftIndex || i == yuIndex) {
                    paintText.setColor(context.getResources().getColor(R.color.textLeftColor));
                } else {
                    paintText.setColor(textColor);
                }
                canvas.drawText(day, parameter.getTextX(i, dayWidth), parameter.getTextY(4), paintText);
            }
        }
    }

    private Bitmap getBitmap(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
        int    width  = bitmap.getWidth();
        int    height = bitmap.getHeight();
        //设置想要的大小
        int newWidth  = 30;
        int newHeight = 30;
        //计算压缩的比率
        float scaleWidth  = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //获取新的bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * 是否点击左面按钮
     *
     * @return
     */
    public boolean getIsLeftClick(float x, float y) {
        if (getLeftStartX() == -1) {
            return false;
        }
        float startX = getLeftStartX() - 20;
        float endX   = getLeftStartX() + leftBitmap.getWidth() + 20;
        float startY = parameter.getImgStartY() - 20;
        float endY   = parameter.getImgStartY() + leftBitmap.getHeight() + 20;
        if (x >= startX && x <= endX && y >= startY && y <= endY) {
            return true;
        }
        return false;
    }

    /**
     * 是否点击右面按钮
     *
     * @return
     */
    public boolean getIsRightClick(float x, float y) {
        if (getRightStartX() == -1) {
            return false;
        }
        float startX = getRightStartX() - 20;
        float endX   = getRightStartX() + rightBitmap.getWidth() + 20;
        float startY = parameter.getImgStartY() - 20;
        float endY   = parameter.getImgStartY() + rightBitmap.getHeight() + 20;
        if (x >= startX && x <= endX && y >= startY && y <= endY) {
            return true;
        }
        return false;
    }

    /**
     * 获取左面按钮初始x坐标
     *
     * @return
     */
    private float getLeftStartX() {
        if (leftBitmap == null) {
            return -1;
        }
        float x = (parameter.getViewWidth() - titleWidth) / 2 - leftBitmap.getWidth() / 2 - 80;
        return x;
    }

    /**
     * 获取右面按钮初始x坐标
     *
     * @return
     */
    private float getRightStartX() {
        if (rightBitmap == null) {
            return -1;
        }
        float x = (parameter.getViewWidth() + titleWidth) / 2 - rightBitmap.getWidth() / 2 + 80;
        return x;
    }

    /**
     * 设置字号
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
        paintText.setTextSize(textSize);
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        paintText.setColor(textColor);
    }

    /**
     * 设置标题文字大小
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        paintTitle.setTextSize(titleTextSize);
    }

    /**
     * 设置标题文字颜色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        paintTitle.setColor(titleTextColor);
    }

    /**
     * 设置选中文字颜色
     *
     * @param checkTextColor
     */
    public void setCheckTextColor(int checkTextColor) {
        this.checkTextColor = checkTextColor;
    }

    /**
     * 设置选中背景色
     *
     * @param checkTextBgColor
     */
    public void setCheckTextBgColor(int checkTextBgColor) {
        this.checkTextBgColor = checkTextBgColor;
        paintCircle.setColor(checkTextBgColor);
    }

    /**
     * 设置左面按钮图片
     *
     * @param leftImg
     */
    public void setLeftImg(int leftImg) {
        this.leftImg = leftImg;
    }

    /**
     * 设置右面按钮图片
     *
     * @param rightImg
     */
    public void setRightImg(int rightImg) {
        this.rightImg = rightImg;
    }

    /**
     * 设置右面不可点击按钮图片
     *
     * @param rightHintImg
     */
    public void setRightHintImg(int rightHintImg) {
        this.rightHintImg = rightHintImg;
    }
}
