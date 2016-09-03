package com.chenhong.android.carsdoor.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.PictureActivity;
import com.chenhong.android.carsdoor.adapter.AbstractPagerListAdapter;
import com.chenhong.android.carsdoor.adapter.BasePagerAdapter;
import com.chenhong.android.carsdoor.dao.dal.INewsPictureDao;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.exception.DataNotFoundException;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 轮播图View 直接包裹在这个framelayout中 自己写的容易出异常 remove parent
 * Created by Android on 2016/7/25.
 */
public class MyScrollPager  extends FrameLayout{
    private Context context;
    // 自动轮播定时器
    private ScheduledExecutorService scheduledExecutorService;
    private List<ImageView> imageViewList=new ArrayList<ImageView>();
    private String[] bmobFileurl=new String[5];
    private List<View> dotViewsList=new ArrayList<View>();
    private ViewPager viewPager;
    private int realposition;
    private String[] bmobFileString=new String[5];
    private List<NewsTitle> newsTitleslist=new ArrayList<>();
   private Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
       }
   };
    private TextView tv_title;


    public MyScrollPager(Context context, AttributeSet attrs) {
        super(context, attrs);
       this.context=context;
//        initdata();
        initview();
        startScroll();
    }
    public MyScrollPager(Context context) {
        super(context);
        this.context=context;
//        initdata();
        initview();
        startScroll();
    }
    public MyScrollPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
//        initdata();
        initview();
        startScroll();
    }

    private void startScroll() {
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(Constant.SCROLLMESSAGE);
            }
        },5,5, TimeUnit.SECONDS);
    }

//    private void initdata() {
//        newsBiz=new NewsBiz(context);
//        try {
//            newsBiz.queryAllHeadPictures().findObjects(new FindListener<NewsTitle>() {
//                @Override
//                public void done(List<NewsTitle> list, BmobException e) {
//                    if(list.size()>0){
//                        newsTitleslist=list;
//                        for(int i=0;i<5;i++){
//                            bmobFileurl[i]=list.get(i).getImage().getFileUrl();
//                            bmobFileString[i]=list.get(i).getUrl();
//                        }
//                    }
//                }
//            });
//        } catch (DataNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        for(int i=0;i<bmobFileString.length;i++){
//            ImageView imageView=new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageViewList.add(imageView); //显示图片的配置
//        }
//
//
//
//    }

    private void initview() {
        //将layout与本身的ViewGroup绑定
        LayoutInflater.from(context).inflate(R.layout.myview_scrollview,this,true);
        viewPager = (ViewPager) findViewById(R.id.news_vp);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(5000);
        viewPager.setOnPageChangeListener(new MyPageChangedListener());
        tv_title = (TextView) findViewById(R.id.news_tv_title);
        dotViewsList.add(findViewById(R.id.news_title_tv1));
        dotViewsList.add(findViewById(R.id.news_title_tv2));
        dotViewsList.add(findViewById(R.id.news_title_tv3));
        dotViewsList.add(findViewById(R.id.news_title_tv4));
        dotViewsList.add(findViewById(R.id.news_title_tv5));

    }



    class MyPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            realposition=position%bmobFileurl.length;
//            View layout =View.inflate(context,R.layout.news_head_item, null);
//            ImageView imageView = (ImageView) layout.findViewById(R.id.viewpager_item_img);
            ImageView imageView=imageViewList.get(realposition);
            ImageLoader.getInstance().displayImage(bmobFileurl[realposition], imageView, Constant.options);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putStringArray("bmobFileurl",bmobFileurl);//图片地址
                    bundle.putStringArray("bmobFileString",bmobFileString);//内容地址
                    bundle.putInt("currentposition",viewPager.getCurrentItem()%bmobFileString.length);
                    PictureActivity.startActivity(context,bundle);
                }
            });
            container.addView(imageView);
//            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            realposition=position%bmobFileurl.length;
            container.removeView(imageViewList.get(realposition));
        }
    }














      class MyPageChangedListener implements ViewPager.OnPageChangeListener{


          @Override
          public void onPageScrolled(int i, float v, int i1) {

          }

          @Override
          public void onPageSelected(int position) {
              realposition=position%bmobFileString.length;
              for(int i=0;i<dotViewsList.size();i++){
                  if(realposition==i){
                      tv_title.setText(bmobFileString[i]);
                      dotViewsList.get(i).setBackgroundColor(getResources().getColor(R.color.news_title_choose));
                  }else {
                      dotViewsList.get(i).setBackgroundColor(getResources().getColor(R.color.news_title_unchoose));
                  }
              }
          }

          @Override
          public void onPageScrollStateChanged(int i) {

          }
      }


}
