package com.chenhong.android.carsdoor.activity;






import android.support.v4.app.Fragment;
import  android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.factory.FragmentFactory;
import com.chenhong.android.carsdoor.fragment.selffragments.LoginFragment;
import com.chenhong.android.carsdoor.fragment.selffragments.LoginSecondFragment;
import com.chenhong.android.carsdoor.global.Constant;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Administrator on 2016/8/2.
 */
public class LoginActivity extends BaseActivity{

    public LoginFragment loginFragment;
    public LoginSecondFragment loginSecondFragment;
    public Fragment currentFragment;
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;



    @Override
    protected void initView() {
       loginFragment= (LoginFragment) FragmentFactory.newInstance(Constant.LOGIN_ONE);
        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_self_flyt_login, loginFragment);
        ft.commit();
    }


    @OnClick(R.id.iv_back)
    public void onClick(View view){
        finish();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_self;
    }


    public void changeToClassifyFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (loginSecondFragment== null) {
            loginSecondFragment = (LoginSecondFragment) FragmentFactory.newInstance(Constant.LOGIN_TWO);
        }
        currentFragment =changFragmentByTag(loginFragment, loginSecondFragment, ft, "HiliFragment");
    }
    public  Fragment changFragmentByTag(Fragment currFragment, Fragment chooseFragment, FragmentTransaction ft,String TAG) {
        if (currFragment != chooseFragment) {
            ft.hide(currFragment);
            if (chooseFragment.isAdded()) {
                ft.show(chooseFragment);
            } else {
                ft.add(R.id.fragment_self_flyt_login, chooseFragment, TAG);
            }
        }
        ft.commitAllowingStateLoss();
        return chooseFragment;
    }
}



