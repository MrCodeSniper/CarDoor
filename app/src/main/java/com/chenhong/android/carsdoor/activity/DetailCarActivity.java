package com.chenhong.android.carsdoor.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.fragment.cardetailfragment.DetailFragment;
import com.chenhong.android.carsdoor.fragment.cardetailfragment.MyFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mob.tools.gui.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Android on 2016/8/11.
 */
public class DetailCarActivity extends BaseActivity{
    @ViewInject(R.id.tl_8)
    private SlidingTabLayout slidingTabLayout;
    @ViewInject(R.id.vp)
    private ViewPager vp;

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.tv_login_text)
    private TextView tv_title;
    @ViewInject(R.id.iv_red_price)
    private ImageView iv_red_price;


    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private final String[] mTitles = {
            "综述","配置","图片","口碑"
            ,"资讯","油耗"};
    @Override
    protected void initView() {


//        ScaleAnimation animation=new ScaleAnimation(0.0f,1.4f,0.0f,1.4f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f);
//        animation.setDuration(1000);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        iv_red_price.setAnimation(animation);
        animation.start();


        tv_title.setText(getIntent().getStringExtra("car"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (int i=0;i<mTitles.length;i++) {
            if(i==0){
                 mFragments.add(new DetailFragment());
            }else {
                mFragments.add(new MyFragment());
            }

        }
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slidingTabLayout.setViewPager(vp,mTitles,this,mFragments);
        vp.setCurrentItem(0);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_detail_car;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
