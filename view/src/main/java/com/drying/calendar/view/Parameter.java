package com.drying.calendar.view;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/26 21:03
 * <p/>
 * Description:计算工具类
 */
public class Parameter {
    /** 时间数据列表 */
    private List<CalendarMo> data;
    /** 初始化添加日期长度 */
    private              int  initItemCcount = 14;
    /** 一天时间的毫秒 */
    private static final long oneDayTime     = 1000 * 60 * 60 * 24;
    /** 空间宽度 */
    private float viewWidth;
    /** 空间高度 */
    private float viewHeight;
    /** 一个日期的宽度 */
    private float itemWidth;
    /** 按下x坐标 */
    private float downX;
    /** 按下x坐标 */
    private float downX1;
    /** 偏移量 */
    private float offset;
    /** 当前控件的x */
    private float curX = 0;
    /** 最大偏移量 */
    private float maxOffset;
    private long  time;
    /** 一屏显示数量 */
    private int     maxItem      = 7;
    /** 占比分份 */
    private int     heightTotal  = 5;
    /** 左面按钮是否可以点击 */
    private boolean isLeftClick  = true;
    /** 右面按钮是否可以点击 */
    private boolean isRightClick = false;
    /** 是否允许滑动 */
    private boolean isNotMove;

    public Parameter() {
        data = new ArrayList<>();
        addData(initItemCcount);
    }

    public List<CalendarMo> getData() {
        return data;
    }

    private void addData(int size) {
        if (time == 0) {
            time = System.currentTimeMillis();
        }
        int startIndex = data.size();
        for (int i = startIndex; i < startIndex + size; i++) {
            CalendarMo mo = new CalendarMo();
            mo.setCheck(i == 0 ? true : false);
            mo.setWeek(DateUtil.getWeek(time));
            mo.setYear(DateUtil.getTime(DateUtil.Format.YEAR, time));
            mo.setMonth(DateUtil.getTime(DateUtil.Format.MONTH, time));
            mo.setDay(DateUtil.getTime(DateUtil.Format.DAY, time));
            data.add(mo);
            time -= oneDayTime;
        }
        maxOffset = data.size() * itemWidth;
    }

    /** 设置每个日期的宽度 */
    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
        /**设置最大偏移量*/
        setMaxOffset(itemWidth * data.size());
    }

    /** 获取控件宽度 */
    public float getViewWidth() {
        return viewWidth;
    }

    /** 设置控件宽度 */
    public void setViewWidth(float viewWidth) {
        this.viewWidth = viewWidth;
        /**设置item的宽度*/
        setItemWidth(viewWidth / maxItem);
    }

    /** 获取控件高度 */
    public float getViewHeight() {
        return viewHeight;
    }

    /** 设置控件高度 */
    public void setViewHeight(float viewHeight) {
        this.viewHeight = viewHeight;
    }

    /** 获取按下X坐标（移动会发生改变） */
    public float getDownX() {
        return downX;
    }

    /** 设置按下X坐标 */
    public void setDownX(float downX) {
        this.downX = downX;
    }

    /** 获取按下X1坐标(移动也不会发生改变) */
    public float getDownX1() {
        return downX1;
    }

    /** 设置按下X1坐标 */
    public void setDownX1(float downX1) {
        this.downX1 = downX1;
    }

    /** 获取偏移量 */
    public float getOffset() {
        return offset;
    }

    /** 设置偏移量 */
    public void setOffset(float x, boolean isMove) {
        this.offset = x - downX;
        /**设置按下是的x坐标*/
        setDownX(x);
        /**判断如果可以滑动，设置滑动后的x坐标*/
        if (maxOffset >= curX + offset && curX + offset >= 0) {
            /**判断是移动还是 手指抬起事件  手指抬起 则靠近item移动*/
            if (isMove) {
                setAddCurX(offset, isMove);
            } else {
                float yu = (curX + offset) % itemWidth;
                if (yu >= itemWidth / 2) {
                    setCurX(((int) ((curX + offset) / itemWidth) + 1) * itemWidth);
                } else {
                    setCurX(((int) ((curX + offset) / itemWidth) * itemWidth));
                }
            }
        }
    }

    /** 获取当前控件的X */
    public float getCurX() {
        return curX;
    }

    /** 设置当前控件的X */
    public void setCurX(float curX) {
        this.curX = curX;
    }

    /** 设置当前控件的滑动后的X */
    public void setAddCurX(float curX, boolean isMove) {
        this.curX += curX;
        /**清楚误差*/
        if (!isMove && curX < 10) {
            this.curX = 0;
        }
    }

    /** 获取最大偏移量 */
    public float getMaxOffset() {
        return maxOffset;
    }

    /** 设置最大偏移量 */
    public void setMaxOffset(float maxOffset) {
        this.maxOffset = maxOffset;
    }

    /**
     * 获取数据条数
     *
     * @return
     */
    public int getDataSize() {
        return data.size();
    }

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean getDataIsNull() {
        return data == null || data.size() == 0;
    }

    /**
     * 获取当前选中的下标
     *
     * @return
     */
    private int getIndex(float x) {
        return (int) ((viewWidth - (x - curX)) / itemWidth);
    }

    /**
     * 选择后更新数据
     */
    public CalendarMo toCheckUpData(float x) {
        int index = getIndex(x);
        for (int i = 0; i < data.size(); i++) {
            if (i == index) {
                data.get(i).setCheck(true);
            } else {
                data.get(i).setCheck(false);
            }
        }
        return data.get(index);
    }

    /**
     * 通过下标获取数据
     *
     * @param position
     *
     * @return
     */
    public CalendarMo getData(int position) {
        return data.get(position);
    }

    /**
     * 判断是否需要增加数据如需要则增加
     */
    public void checkAddData() {
        int num = data.size() / 7;
        if (num - curX / viewWidth < 1.5) {
            addData(14);
        }
    }

    /**
     * 获取当前屏幕展示的某个位置在列表中的下标
     *
     * @param index
     *         屏幕中第几个位置
     *
     * @return
     */
    public int getNewIndexPosition(int index) {
        int position = (int) (curX / itemWidth) + maxItem - index;
        return position;
    }

    /**
     * 获取当前屏幕显示的某个位置数据
     *
     * @param index
     *
     * @return
     */
    public CalendarMo getIndexData(int index) {
        return data.get(getNewIndexPosition(index));
    }

    /**
     * 获取按钮开始的Y坐标
     *
     * @return
     */
    public float getImgStartY() {
        float y = viewHeight / heightTotal * 1;
        return y;
    }

    /**
     * 画页面时获取控件的x坐标
     *
     * @param index
     *         控件下标
     * @param textWidth
     *         文字宽度
     *
     * @return
     */
    public float getTextX(int index, float textWidth) {
        return viewWidth - (index * itemWidth + (itemWidth + textWidth) / 2) + curX;
    }

    /**
     * 获取画控件的Y坐标
     *
     * @return
     */
    public float getTextY(int num) {
        return viewHeight / heightTotal * num;
    }

    /**
     * 获取标题X坐标
     *
     * @param titleWidth
     *         标题文字的宽度
     *
     * @return
     */
    public float getTitleX(float titleWidth) {
        return (viewWidth - titleWidth) / 2 - 20;
    }

    /**
     * 获取标题Y坐标
     *
     * @param leftImgHeight
     *
     * @return
     */
    public float getTitleY(float leftImgHeight) {
        return viewHeight / heightTotal * 1 + leftImgHeight;
    }

    /**
     * 向左移动一个月
     */
    public void toLeftMove(View view) {
        /**可移动量*/
        float x = maxOffset - curX;
        /**需要移动的量 （需要预留10个）*/
        float y   = itemWidth * 40;
        int   add = (int) ((y - x) / itemWidth);
        /** 如果预加载数据不足则补齐 */
        addData(add);
        /**移动*/
        for (int i = 0; i < 30; i++) {
            curX += itemWidth;
            view.invalidate();
        }
    }

    /**
     * 向右移动一个月
     */
    public boolean toRightMove(View view) {
        for (int i = 0; i < 30; i++) {
            if (curX > 0) {
                curX -= itemWidth;
                view.invalidate();
            }
        }
        if (curX < 10) {
            curX = 0;
            return false;
        }
        return true;
    }

    /**
     * 获取选中圆x
     *
     * @param i
     *
     * @return
     */
    public float getCircleX(int i) {

        return viewWidth - (i * itemWidth + itemWidth / 2) + curX;
    }

    /**
     * 获取选中圆y
     *
     * @return
     */
    public float getCircleY(int textSize) {

        return viewHeight / heightTotal * 4 - (textSize - 4) / 2;
    }

    /**
     * 获取选中圆r
     *
     * @return
     */
    public float getCircleR() {

        return viewHeight /
                heightTotal / 2;
    }

    public int getHeightTotal() {
        return heightTotal;
    }

    /**
     * 左面按钮是否可以点击
     *
     * @return
     */
    public boolean isLeftClick() {
        return isLeftClick;
    }

    public void setLeftClick(boolean leftClick) {
        isLeftClick = leftClick;
    }

    /**
     * 右面按钮是否可以点击
     *
     * @return
     */
    public boolean isRightClick() {
        return isRightClick;
    }

    public void setRightClick(boolean rightClick) {
        isRightClick = rightClick;
    }

    /**
     * 是否允许滑动
     *
     * @return
     */
    public boolean isNotMove() {
        return isNotMove;
    }

    public void setNotMove(float y) {
        if (y > viewHeight / 5 * 3 - 50 && y < viewHeight / 5 * 4 + 50) {
            isNotMove = true;
        } else {
            isNotMove = false;
        }
    }
}
