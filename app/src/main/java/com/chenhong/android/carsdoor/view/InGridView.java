package com.chenhong.android.carsdoor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ListView中嵌套一个GridView制作“九宫格”的效果，结果GridView上的子元素仅仅显示了一行，没有显示完整
 * 就是重写一个GridView，做一个自定义的GridView，然后重写GridView的onMeasure(int widthMeasureSpec, int heightMeasureSpec)的方法，将GridView重新测量，并且指定GridView的新的高度
 * Created by Android on 2016/8/16.
 */
public class InGridView extends GridView {

    public InGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public InGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public InGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}