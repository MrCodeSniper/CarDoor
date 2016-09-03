package com.chenhong.android.carsdoor.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.FixedEditText.FixedEditText;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Android on 2016/8/2.
 */
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.tv_login_text)
    private TextView tv_login_text;
    @ViewInject(R.id.et_phone)
    private FixedEditText ft_tel;
    @ViewInject(R.id.et_pd)
    private FixedEditText ft_pd;
    @ViewInject(R.id.et_yanzhen)
    private FixedEditText ft_yanzhen;
    @ViewInject(R.id.tv_getyanzhen)
    private TextView tv_getyanzhen;
    private CountTimer countTimer;

    @ViewInject(R.id.btn_commit)
    private Button btn_commit;
    private String tel;
    private EventHandler eh;


    @Override
    protected void initView() {
        initLayout();
        countTimer = new CountTimer(60000,1000);
        SMSSDK.initSDK(RegisterActivity.this, "15b59dd4562e4", "807e41327637352c3c994cb940407a32");
    }


    @OnClick({R.id.tv_getyanzhen,R.id.btn_commit})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.tv_getyanzhen:
                tel = ft_tel.getText().toString();
                 if(TextUtils.isEmpty(tel)){
                     Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                 }else if(!MyUtils.isMobileNO(tel)){
                     Toast.makeText(RegisterActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                 }else{
                     //开启倒计时
                     Toast.makeText(RegisterActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                     countTimer.start();
                     sendSMSRandom();
                 }
                break;
            case R.id.btn_commit:
                if(TextUtils.isEmpty(ft_tel.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else if(!MyUtils.isMobileNO(ft_tel.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(ft_pd.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(ft_yanzhen.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }else {
                    //验证验证码
                    SMSSDK.submitVerificationCode("86",tel,ft_yanzhen.getText().toString());
                }
                break;

            default:

                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    private void sendSMSRandom(){
        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.e("tazz", "验证码校验成功");
                       _User user=new _User();
                        user.setUsername(ft_tel.getText().toString());
                        user.setMobilePhoneNumber(ft_tel.getText().toString());
                        user.setPassword(ft_pd.getText().toString());
                        user.setMobilePhoneNumberVerified(true);
                        user.setCity("请设置所在地");
                        user.signUp(new SaveListener<_User>() {
                            @Override
                            public void done(_User user, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.e("tazzz",e.getMessage());
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Log.e("tazz", "验证码发送成功");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    //验证码验证失败
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        //发送验证码

        SMSSDK.getVerificationCode("86", ft_tel.getText().toString());



    }










  class CountTimer extends CountDownTimer{
      /**
       * @param millisInFuture    The number of millis in the future from the call
       *       相隔的时间                to {@link #start()} until the countdown is done and {@link #onFinish()}
       *                          is called.
       * @param countDownInterval The interval along the way to receive
       *        回调ontick方法              {@link #onTick(long)} callbacks.
       */
      public CountTimer(long millisInFuture, long countDownInterval) {
          super(millisInFuture, countDownInterval);
      }
      //间隔时间内执行的操作
      @Override
      public void onTick(long millisUntilFinished) {
        tv_getyanzhen.setText(millisUntilFinished/1000+"S后发送");
          tv_getyanzhen.setClickable(false);

      }
      //间隔时间结束调用的方法
      @Override
      public void onFinish() {
          tv_getyanzhen.setText("点击获取验证码");
          tv_getyanzhen.setClickable(true);
      }
  }









    private void initLayout() {
        tv_login_text.setText("注册");
        ft_tel.setFixedText("    手机号");
        ft_pd.setFixedText("   密码");
        ft_yanzhen.setFixedText("   验证码");

        SpannableString ss_tel = new SpannableString("请输入手机号");//定义hint的值
        SpannableString ss_pd = new SpannableString("请输入密码");//定义hint的值
        SpannableString ss_yanzhen = new SpannableString("输入手机验证码");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14,true);//设置字体大小 true表示单位是sp
        ss_tel.setSpan(ass, 0, ss_tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_pd.setSpan(ass, 0, ss_pd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_yanzhen.setSpan(ass, 0, ss_yanzhen.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ft_tel.setHint(ss_tel);
        ft_pd.setHint(ss_pd);
        ft_yanzhen.setHint(ss_yanzhen);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }
}
