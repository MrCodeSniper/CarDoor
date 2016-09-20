package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.adsmogo.offers.MogoOffer;
import com.adsmogo.offers.MogoOfferPointCallBack;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.ui.Colorful;
import com.chenhong.android.carsdoor.ui.setter.ViewGroupSetter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Android on 2016/9/5.
 */
public class SettingActivity extends BaseActivity implements MogoOfferPointCallBack,Switch.OnCheckedChangeListener{

@ViewInject(R.id.rl_app)
private RelativeLayout rl_app;
    boolean isNight = false ;
    private Colorful mColorful;

    @ViewInject(R.id.swit_ch)
    private Switch aSwitch;
    @ViewInject(R.id.root_view)
    private LinearLayout ll_root_view;
    Activity activity;
    public static String mogoID = "39658de63f2747da9fcc111f98b539cd";
    @Override
    protected void initView() {
        activity = this;
        MogoOffer.init(SettingActivity.this,mogoID);
        MogoOffer.setOfferListTitle("获取积分");
        MogoOffer.setOfferEntranceMsg("商城");
        MogoOffer.setMogoOfferScoreVisible(false);
        MogoOffer.addPointCallBack(this);

        initNight();


        aSwitch.setOnCheckedChangeListener(this);


    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_setting;
    }



    @OnClick(R.id.rl_app)
    public void onClick(View view){
        MogoOffer.showOffer(activity);
    }


    @Override
    public void updatePoint(long point) {
//        showPointTxt.setText("point:" + point);
    }

    @Override
    protected void onDestroy()
    {
        MogoOffer.clear(this);

        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MogoOffer.RefreshPoints(activity);
    }

    private void initNight() {
        //        // 为ListView设置要修改的属性,在这里没有对ListView本身的属性做修改
        ViewGroupSetter listViewSetter = new ViewGroupSetter(ll_root_view, 0);
//// 绑定ListView的Item View中的news_title视图，在换肤时修改它的text_color属性
        listViewSetter.childViewTextColor(R.id.tv_app, R.attr.text_color);
        listViewSetter.childViewTextColor(R.id.tv_night, R.attr.text_color);
        // 构建Colorful对象
        // 设置view的背景图片
//                .backgroundColor(R.id.change_btn, R.attr.btn_bg) // 设置按钮的背景色
// 设置文本颜色
//                .setter(listViewSetter)           // 手动设置setter
        mColorful = new Colorful.Builder(this)
                .backgroundDrawable(R.id.root_view, R.attr.root_view_bg) // 设置view的背景图片
//                .backgroundColor(R.id.change_btn, R.attr.btn_bg) // 设置按钮的背景色
                .textColor(R.id.tv_app, R.attr.text_color) // 设置文本颜色
                .textColor(R.id.tv_night,R.attr.text_color)
//                .setter(listViewSetter)           // 手动设置setter
                .create();
    }



    // 切换主题
    private void changeThemeWithColorful() {
        if (!isNight) {
            mColorful.setTheme(R.style.DayTheme);
        } else {
            mColorful.setTheme(R.style.NightTheme);
        }
        isNight = !isNight;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            isNight=true;
            changeThemeWithColorful();
        }else {
            isNight=false;
            changeThemeWithColorful();
        }
    }


}
