package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.adsmogo.offers.MogoOffer;
import com.adsmogo.offers.MogoOfferPointCallBack;
import com.chenhong.android.carsdoor.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Android on 2016/9/5.
 */
public class SettingActivity extends BaseActivity implements MogoOfferPointCallBack {

@ViewInject(R.id.rl_app)
private RelativeLayout rl_app;
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




}
