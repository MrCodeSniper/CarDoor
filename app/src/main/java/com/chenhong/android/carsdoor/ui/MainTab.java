package com.chenhong.android.carsdoor.ui;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.fragment.FindFragment;
import com.chenhong.android.carsdoor.fragment.NewsFragment;
import com.chenhong.android.carsdoor.fragment.PriceFragment;
import com.chenhong.android.carsdoor.fragment.QuestionFragment;
import com.chenhong.android.carsdoor.fragment.SelfFragment;

/**
 * Created by Administrator on 2016/7/28.
 */
public enum  MainTab {

    NEWS(0, R.string.car_str_news, R.drawable.car_news_selector,
            NewsFragment.class),

    FIND(1, R.string.car_str_find, R.drawable.car_find_selector,
            FindFragment.class),

    PRICE(2, R.string.car_str_price, R.drawable.car_price_selector,
            PriceFragment.class),

    QUES(3, R.string.car_str_question, R.drawable.car_question_selector,
            QuestionFragment.class),

    ME(4, R.string.car_str_self, R.drawable.car_self_selector,
            SelfFragment.class);



    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;


    MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }


    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
