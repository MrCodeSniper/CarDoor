package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.utils.ActivityStack;
import com.hp.hpl.sparta.Text;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/8/14.
 */
public class SelfDetailActivity extends BaseActivity {


    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.tv_update)
    private TextView tv_update;
    @ViewInject(R.id.user_image)
    private ImageView user_image;
    @ViewInject(R.id.tv_username)
    private TextView tv_username;
    @ViewInject(R.id.tv_city)
    private TextView tv_city;





    @Override
    protected void initView() {






        //当系统大于4.4以上时才支持 状态栏透明
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarAlpha(0.0f);
        }

        setResult(100);


        if(BmobUser.getCurrentUser(_User.class)!=null){
            if((String)BmobUser.getObjectByKey("nick")!=null){
                tv_username.setText((String)BmobUser.getObjectByKey("nick"));
            }else {
                tv_username.setText((String)BmobUser.getObjectByKey("username"));
            }


            if((String)BmobUser.getObjectByKey("icon")!=null){
                ImageLoader.getInstance().displayImage((String)BmobUser.getObjectByKey("icon"),user_image);
            }


            if(TextUtils.isEmpty((String) BmobUser.getObjectByKey("city"))||(String) BmobUser.getObjectByKey("city")==null){
                tv_city.setText("请设置所在城市");
            }else {
                tv_city.setText((String) BmobUser.getObjectByKey("city"));
            }
            tv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SelfDetailActivity.this,UpdateSelfActivity.class);
                    startActivity(intent);
                }
            });

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();


        if((String)BmobUser.getObjectByKey("city")!=null){
            tv_city.setText((String)BmobUser.getObjectByKey("city"));
        }
        if((String)BmobUser.getObjectByKey("nick")!=null){
            tv_username.setText((String)BmobUser.getObjectByKey("nick"));
        }

        if((String)BmobUser.getObjectByKey("icon")!=null){
            ImageLoader.getInstance().displayImage((String)BmobUser.getObjectByKey("icon"),user_image);
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_selfdetail;
    }



}
