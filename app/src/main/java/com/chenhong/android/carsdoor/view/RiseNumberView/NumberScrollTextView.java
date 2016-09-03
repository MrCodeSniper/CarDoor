package com.chenhong.android.carsdoor.view.RiseNumberView;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;

import java.text.DecimalFormat;



/**
 * NumberScrollTextView,用属性动画完成数字平滑滚动
 * created by shidong
 */
public class NumberScrollTextView extends TextView  {

    private static final int STOPPED = 0;

    private static final int RUNNING = 1;

    private int mPlayingState = STOPPED;

    private float number;

    private float fromNumber;

    /**
     * 默认时长
     */
    private long duration = 1000;
    /**
     * 1.int 2.float
     */
    private int numberType = 2;

    private DecimalFormat fnum;

    private EndListener mEndListener = null;

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    /**
     * 构造方法
     *
     * @param context
     */
    public NumberScrollTextView(Context context) {
        super(context);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attr
     */
    public NumberScrollTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public NumberScrollTextView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    /**
     * 动画是否正在执行
     *
     * @return
     */
    public boolean isRunning() {
        return (mPlayingState == RUNNING);
    }

    /**
     * 浮点型数字变动
     */
    private void runFloat() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromNumber, number);
        valueAnimator.setDuration(duration);

        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        setText(fnum.format(Float.parseFloat(valueAnimator
                                .getAnimatedValue().toString())));
                        if (valueAnimator.getAnimatedFraction() >= 1) {
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                mEndListener.onEndFinish();
                        }
                    }

                });

        valueAnimator.start();
    }

    /**
     * 整型数字变动
     */
    private void runInt() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) fromNumber,
                (int) number);
        valueAnimator.setDuration(duration);

        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        setText(valueAnimator.getAnimatedValue().toString());
                        if (valueAnimator.getAnimatedFraction() >= 1) {
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                mEndListener.onEndFinish();
                        }
                    }
                });
        valueAnimator.start();
    }

    static int sizeOfInt(int x) {
        for (int i = 0; ; i++) {
            if (x <= sizeTable[i])
                return i + 1;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fnum = new DecimalFormat("##0.00");
    }

    /**
     * 开始动画
     */
    public void start() {

        if (!isRunning()) {
            mPlayingState = RUNNING;
            if (numberType == 1)
                runInt();
            else
                runFloat();
        }
    }


    /**
     * 设置数字
     *
     * @param number
     */
    public void withNumber(int number) {
        this.number = number;
        numberType = 1;
        if (number > 1000) {
            fromNumber = number
                    - (float) Math.pow(10, sizeOfInt((int) number) - 2);
        } else {
            fromNumber = number / 2;
        }
    }

    /**
     * 设置数字
     *
     * @param number
     */
    public void withNumber(float number) {

        this.number = number;
        numberType = 2;
        if (number > 1000) {
            fromNumber = number
                    - (float) Math.pow(10, sizeOfInt((int) number) - 1);
        } else {
            fromNumber = number / 2;
        }

    }

    public void setFromAndEndNumber(int fromNumber, int endNumber) {
        this.fromNumber = fromNumber;
        this.number = endNumber;
        numberType = 1;
    }

    public void setFromAndEndNumber(float fromNumber, float endNumber) {
        this.fromNumber = fromNumber;
        this.number = endNumber;
        numberType = 2;
    }

    /**
     * 设置动画时长
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 设置动画结束监听
     */
    public void setOnEndListener(EndListener callback) {
        mEndListener = callback;
    }

    /**
     * 动画结束接口
     */
    public interface EndListener {
        /**
         * 动画结束
         */
        public void onEndFinish();
    }

}