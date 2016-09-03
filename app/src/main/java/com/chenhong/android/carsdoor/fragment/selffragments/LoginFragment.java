package com.chenhong.android.carsdoor.fragment.selffragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.LoginActivity;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Administrator on 2016/8/2.
 */
public class LoginFragment extends BaseFragment {

     @ViewInject(R.id.tv_tel_login)
     private TextView tv_tel_login;
    @ViewInject(R.id.ll_qq_login)
    private RelativeLayout rl_qq_login;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other_login;
    }

    @Override
    protected void initParams() {

        tv_tel_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity activity=(LoginActivity)getActivity();
                activity.changeToClassifyFragment();
            }
        });

        rl_qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginByQQ();
            }
        });



    }

    public void loginByQQ(){
        Platform platform= ShareSDK.getPlatform(getActivity(), QQ.NAME);
        //得到支持
        //增加监听授权
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String username=platform.getDb().getUserName();
                String userIcon = platform.getDb().getUserIcon();
                Log.e("tazz",username+","+userIcon);
                Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(getActivity(),"授权失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(getActivity(),"授权已取消",Toast.LENGTH_SHORT).show();
            }
        });
       //判断是否正常登陆
        if(platform.isValid()){
            String username=platform.getDb().getUserName();

        }else {
           platform.showUser(null);
        }



    }





    @Override
    protected void initLoading() {

    }
}
