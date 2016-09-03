package com.chenhong.android.carsdoor.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.BitmapUtils;
import com.chenhong.android.carsdoor.view.indicator.CirclePageIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.InputStream;

/**
 * Created by Android on 2016/8/1.
 */
public class SplashActivity extends BaseActivity {
    private FrameLayout layou5;
    private Button guide_btn_start;
    private ImageView guide_iv5;

    private SharedPreferences sp;
    @ViewInject(R.id.guide_vp)
    private ViewPager vp_guide;

    @ViewInject(R.id.guide_vp_indicator)
    private CirclePageIndicator circlePageIndicator;

    private int[] res=new int[]{R.drawable.guide_bg1,R.drawable.guide_bg2,R.drawable.guide_bg3,
            R.drawable.guide_bg4,R.drawable.guide_bg5
    };
    private Bitmap bitmap;


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
        vp_guide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return res.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = (View) object;
                container.removeView(view);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

               if(position==res.length-1){
                   layou5 = (FrameLayout) getLayoutInflater().inflate(R.layout.guide_item5, null);
                   guide_iv5=(ImageView) layou5.findViewById(R.id.guide_iv5);
                   bitmap = BitmapUtils.readBitMap(SplashActivity.this,res[position]);
//                   guide_iv5.setImageResource(res[position]);//会卡图片太大
                   guide_iv5.setImageBitmap(bitmap);
                   guide_btn_start = (Button) layou5.findViewById(R.id.guide_btn_start);
                   guide_btn_start.setOnClickListener(new View.OnClickListener()
                   {
                       @Override
                       public void onClick(View v)
                       {
                           Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                           startActivity(intent);
                           sp.edit().putBoolean("isFirst",false).commit();
                           finish();
                       }
                   });
                   container.addView(layou5);
                   return layou5;
               }else {
                   ImageView imageView=new ImageView(SplashActivity.this);
                   bitmap = BitmapUtils.readBitMap(SplashActivity.this,res[position]);
                   imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                   imageView.setImageBitmap(bitmap);
                   container.addView(imageView);
                   return imageView;
               }
            }
        });

        circlePageIndicator.setViewPager(vp_guide);





    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayoutID() {
        return R.layout.guide_main;
    }
}
