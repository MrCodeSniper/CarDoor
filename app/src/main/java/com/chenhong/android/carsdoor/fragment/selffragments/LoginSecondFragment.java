package com.chenhong.android.carsdoor.fragment.selffragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.RegisterActivity;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.view.FixedEditText.FixedEditText;
import com.dd.CircularProgressButton;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/8/2.
 */
public class LoginSecondFragment extends BaseFragment{
    @ViewInject(R.id.et_phone)
    private FixedEditText ft_tel;
    @ViewInject(R.id.et_pd)
    private FixedEditText ft_pd;
    @ViewInject(R.id.tv_register)
    private TextView tv_register;
    @ViewInject(R.id.btn_login)
    private CircularProgressButton btn_login;

    private SharedPreferences sp;
    private ValueAnimator widthAnimation;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                    sp.edit().putString("UserName",ft_tel.getText().toString());
                    Intent intent=new Intent();
                    intent.putExtra("username",ft_tel.getText().toString());
                    getActivity().setResult(Constant.LOGIN_REQUEST,intent);
                    getActivity().finish();
                    break;
                case 0:
                    //TODO
                    break;
            }

        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tel_login;
    }

    @Override
    protected void initParams() {
        btn_login.setIndeterminateProgressMode(true);
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        ft_tel.setFixedText("    手机号");
        ft_pd.setFixedText("   密码");


    }
    @OnClick(R.id.btn_login)
    public void onclick(View view){

        if(TextUtils.isEmpty(ft_tel.getText().toString())){
            Toast.makeText(getActivity(),"请输入手机号码",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(ft_pd.getText().toString())){
            Toast.makeText(getActivity(),"请输入密码",Toast.LENGTH_SHORT).show();
        }else if(btn_login.getProgress() != 0) {
            btn_login.setProgress(0);
        }else {
            widthAnimation = ValueAnimator.ofInt(1, 99);
            widthAnimation.setDuration(1500);
            widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                btn_login.setProgress(value);
           }
        });
            widthAnimation.start();
            Log.e("tazz",ft_pd.getText().toString());
            _User user = new _User();
            user.setMobilePhoneNumber(ft_tel.getText().toString());
            user.setUsername(ft_tel.getText().toString());
            user.setPassword(ft_pd.getText().toString());

            user.login(new SaveListener<_User>() {
                @Override
                public void done(_User bmobUser, BmobException e) {
                    if(e==null){
//                        simulateSuccessProgress(btn_login);
                        if(widthAnimation.isRunning()){
                            widthAnimation.cancel();
                        }
                        btn_login.setProgress(100);
                        new Thread(new Runnable(){
                            public void run(){
                                try {
                                    Thread.sleep(1600);
                                } catch (InterruptedException e) {
                                    Log.e("TAG", "SLEEP throw e: "+ e.toString());
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }else {
                        if(widthAnimation.isRunning()){
                            widthAnimation.cancel();
                        }
                          btn_login.setProgress(-1);
//                        simulateErrorProgress(btn_login);
                        Toast.makeText(getActivity(),"账户名或密码错误",Toast.LENGTH_SHORT).show();
                        Log.e("tazz",e.getMessage());
                    }
                }
            });
        }
    }



//    private void simulateSuccessProgress(final CircularProgressButton button) {
//        widthAnimation = ValueAnimator.ofInt(1, 100);
//        widthAnimation.setDuration(1500);
//        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer value = (Integer) animation.getAnimatedValue();
//                button.setProgress(value);
//            }
//        });
//        widthAnimation.start();
//
//    }

//    private void simulateErrorProgress(final CircularProgressButton button) {
//        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
//        widthAnimation.setDuration(1500);
//        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer value = (Integer) animation.getAnimatedValue();
//                button.setProgress(value);
//                if (value == 99) {
//                    button.setProgress(-1);
//                }
//            }
//        });
//        widthAnimation.start();
//
//    }



    @Override
    protected void initLoading() {

    }


    @OnClick(R.id.tv_register)
    public void onClick(View view)
    {
        Intent intent=new Intent(getActivity(), RegisterActivity.class);
        startActivityForResult(intent,Constant.LOGIN_MID);
    }



}
