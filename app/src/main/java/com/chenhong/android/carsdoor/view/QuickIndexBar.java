package com.chenhong.android.carsdoor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/6/11.
 */
public class QuickIndexBar extends View {
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private Paint mpaint;
    private int width;
    private float cellheight;
    private int position;

    public QuickIndexBar(Context context) {
        super(context);
        initview();
    }

    private void initview() {

       mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿参数
        mpaint.setColor(Color.BLACK);
        mpaint.setTextSize(30);
        mpaint.setTextAlign(Paint.Align.CENTER);


    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    //在ondraw绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x=width/2;
        for (int i=0;i<indexArr.length;i++){
            float textHeight= getTextHeight(indexArr[i]);
            float y=cellheight/2+textHeight /2+i*cellheight;
            mpaint.setColor(lastposition==i?Color.WHITE:Color.BLACK);
            //把-1变灰
            canvas.drawText(indexArr[i],x,y,mpaint);//预览就能看
        }
    }


    private float getTextHeight(String text) {
        //获取文本高度 rect矩形
        Rect bounds=new Rect();
        mpaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        cellheight = getMeasuredHeight()*1f/26;
    }

  private  int lastposition=-1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(Color.TRANSPARENT);
                lastposition=-1;//手抬起重置
                break;
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(Color.BLACK);
                getBackground().setAlpha(80);
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                position = (int) (y/cellheight);
                 //对下标就行安全性检查
                if(position>=0&&position<indexArr.length){
                    if(lastposition!= position){
                        if(listener!=null){
                            listener.onTouchLetter(indexArr[position]);
                        }
                    }
                }
                lastposition= position;
                break;
        }
        //重绘 回调ondraw
        invalidate();


        return true;//自己处理
    }

    public void setonTouchListener(onTouchLetterListener listener) {
        this.listener = listener;
    }

    private onTouchLetterListener listener;


    public  interface onTouchLetterListener{

        void onTouchLetter(String letter);


    }



}
