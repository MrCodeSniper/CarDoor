package com.chenhong.android.carsdoor.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.global.Constant;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Android on 2016/8/1.
 */
public class LoadingActivity extends BaseActivity {

    @ViewInject(R.id.loading_iv_ad)
    private ImageView mIVEntry;
    private SharedPreferences sp;
    private Intent intent;
    @ViewInject(R.id.view)
    private ImageView view;
    @ViewInject(R.id.ll_title)
    private LinearLayout ll_title;

    private static final int[] Imgs={
            R.drawable.car1,R.drawable.car2,
            R.drawable.car3,R.drawable.car4,
            R.drawable.car5
         };
    private static final int ANIM_TIME = 2000;
    private static final float SCALE_END = 0.1F;

    @Override
    protected void initView() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarAlpha(0.0f);
        }


        sp=getSharedPreferences("config",MODE_PRIVATE);
        /**
         * 随机显示图片
         */

        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        String imageUri = "drawable://" +Imgs[random.nextInt(Imgs.length)]; //  drawable文件
        ImageLoader.getInstance().displayImage(imageUri,mIVEntry, Constant.options);


        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>()
                {
                    @Override
                    public void call(Long aLong)
                    {
                        startAnim();
                    }
                });



    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_loading;
    }


    private void startAnim() {
        ll_title.setVisibility(View.VISIBLE);
        ObjectAnimator animator_title = ObjectAnimator.ofFloat(ll_title, "alpha", 0f, 1f);
        animator_title.setDuration(500);
        animator_title.start();
        animator_title.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, SCALE_END);
                animator.setDuration(ANIM_TIME);
                animator.start();
                animator.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        if(sp.getBoolean("isFirst",true)){
                            intent = new Intent(LoadingActivity.this,SplashActivity.class);
                        }else {
                            intent=new Intent(LoadingActivity.this,MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });




    }
}
