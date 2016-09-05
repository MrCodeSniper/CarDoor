package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adsmogo.offers.MogoOffer;
import com.adsmogo.offers.MogoOfferPointCallBack;
import com.chenhong.android.carsdoor.R;

/**
 * Created by Android on 2016/9/5.
 */
public class MogoActivity extends Activity implements MogoOfferPointCallBack {

    Activity activity;
    private Button showPointsBtn, showOfferBtn, addPoints, spendPoints;
    private TextView showPointTxt;

    public static String mogoID = "39658de63f2747da9fcc111f98b539cd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mogo);
        activity = this;
        MogoOffer.init(MogoActivity.this,mogoID);
        MogoOffer.setOfferListTitle("获取积分");
        MogoOffer.setOfferEntranceMsg("商城");
        MogoOffer.setMogoOfferScoreVisible(false);
        MogoOffer.addPointCallBack(this);
//        MogoOffer.setMogoOfferListCallback(this);

        showOfferBtn = (Button) findViewById(R.id.show_offer_btn);


        showOfferBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                MogoOffer.showOffer(activity);
            }
        });

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
