package com.chenhong.android.carsdoor.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Version;
import com.chenhong.android.carsdoor.global.Common;
import com.chenhong.android.carsdoor.interf.OnTabReselectListener;
import com.chenhong.android.carsdoor.ui.MainTab;
import com.chenhong.android.carsdoor.utils.FileUtils;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener,View.OnTouchListener{

    private Handler handler;
    public FragmentTabHost mTabHost;
//    private LayoutInflater layoutInflater;
//    private Class fragmentArray[] = {NewsFragment.class, FindFragment.class,
//            MyFragment.class, QuestionFragment.class, SelfFragment.class};
//    private String mTextviewArray[] = {"资讯", "找车", "降价", "问答", "我的"};
//    private int mImageViewArray[] = {R.drawable.car_news_selector,
//            R.drawable.car_find_selector, R.drawable.car_price_selector,
//            R.drawable.car_question_selector, R.drawable.car_self_selector};


    @Override
    public int getLayoutID() {
        return R.layout.activity_main_two;
    }


    @Override
    protected void initView() {




//
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setDebugMode(true);
//        mPushAgent.enable();
//
//
//        //开启推送并设置注册的回调处理
//        mPushAgent.enable(new IUmengRegisterCallback() {
//
//            @Override
//            public void onRegistered(final String registrationId) {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //onRegistered方法的参数registrationId即是device_token
//                        Log.d("device_token", registrationId);
//                    }
//                });
//            }
//        });

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        inittab();
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
//        int count = fragmentArray.length;
//        for (int i = 0; i < count; i++) {
//            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
//            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//            mTabHost.getTabWidget().setDividerDrawable(null);
//        }


        initDensityDpi();


//        QueryData queryData=new QueryData(new BmobCallback() {
//            @Override
//            public void LoadComplete(List list) {
//                Log.e("taqqq",list.size()+"");
//            }
//
//            @Override
//            public void LoadError(BmobException e) {
//                Log.e("taqqq",e.getMessage());
//            }
//
//            @Override
//            public void LoadStart(BmobQuery query) {
//                Log.e("taqqq","加载开始");
//                query.order("-bid");
//                query.setSkip(0);
//            }
//        });
//        queryData.queryFirstData();





        BmobQuery<Version> query = new BmobQuery<Version>();
        query.getObject("Lhlr4449", new QueryListener<Version>() {

            @Override
            public void done(Version object, BmobException e) {
                if(e==null){
                   if(!object.getLatest().equals(MyUtils.getVersion(MainActivity.this))) {
                        showUpdateDialog(object);
                   }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });

    }









    private void showUpdateDialog(final Version version) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.toutiao__app_icon);
        builder.setTitle("请升级APP至版本" +version.getLatest());
        builder.setMessage(version.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downFile(version);     //在下面的代码段
                } else {
                    Toast.makeText(MainActivity.this, "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();


    }

    void downFile(final Version version) {
            //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
            File saveFile = new File(Environment.getExternalStorageDirectory(), version.getApkFile().getFilename());
        version.getApkFile().download(saveFile, new DownloadFileListener() {
                private ProgressDialog pBar;
                @Override
                public void onStart() {
                    MyUtils.showToast(MainActivity.this,"开始下载...");
                    //进度条，在下载的时候实时更新进度，提高用户友好度
                    pBar = new ProgressDialog(MainActivity.this);
                    pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pBar.setTitle("正在下载");
                    pBar.setMessage("请稍候...");
                    pBar.setProgress(0);
                    pBar.show();
                }

                @Override
                public void done(String savePath,BmobException e) {
                    if(e==null){
                        pBar.dismiss();
                        FileUtils.update(version.getApkFile().getFilename(),MainActivity.this);
                    }else{
                        pBar.dismiss();
                        MyUtils.showToast(MainActivity.this,"下载失败："+e.getErrorCode()+","+e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value, long newworkSpeed) {
                    Log.i("bmob","下载进度："+value+","+newworkSpeed);
                    pBar.setProgress(value);
                }

            });
    }





    /**
         * 获取手机的屏幕密度DPI
         */
    private void initDensityDpi() {
        // TODO Auto-generated method stub
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Common.Width = metrics.widthPixels;
        Common.Height = metrics.heightPixels;
    }

    private void inittab() {
        MainTab[] tabs = MainTab.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = View.inflate(this, R.layout.tab_item_view, null);
            TextView title = (TextView) indicator.findViewById(R.id.tv_item_tab);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_item_tab);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            //title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.getTabWidget().setDividerDrawable(null);
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }


//    private View getTabItemView(int index) {
//        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_tab);
//        imageView.setImageResource(mImageViewArray[index]);
//        TextView textView = (TextView) view.findViewById(R.id.tv_item_tab);
//        textView.setText(mTextviewArray[index]);
//        return view;
//    }


    @Override
    public void onTabChanged(String tabID) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
            super.onTouchEvent(event);
            boolean consumed = false;
            // use getTabHost().getCurrentTabView to decide if the current tab is
            // touched again
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    && v.equals(mTabHost.getCurrentTabView())) {
                // use getTabHost().getCurrentView() to get a handle to the view
                // which is displayed in the tab - and to get this views context
                Fragment currentFragment = getCurrentFragment();
                if (currentFragment != null
                        && currentFragment instanceof OnTabReselectListener) {
                    OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                    listener.OnTabHostReselect();
                    consumed = true;
                }
            }
            return consumed;
    }




    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {//只要被触摸就会触发 注册了就分发
        if(fragmentOnTouchListener!=null){
              fragmentOnTouchListener.onTouch(ev);
        }
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public FragmentOnTouchListener fragmentOnTouchListener;

    public interface FragmentOnTouchListener{
        public boolean onTouch(MotionEvent event);
    }


    public void registerfragmentOnTouchListener(FragmentOnTouchListener fragmentOnTouchListener){
        this.fragmentOnTouchListener=fragmentOnTouchListener;
    }

    public void UnregisterfragmentOnTouchListener(){
        this.fragmentOnTouchListener=null;
    }




    public interface MyTouchListener {
        public boolean onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove( listener );
    }

//    /**
//     * 分发触摸事件给所有注册了MyTouchListener的接口
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        for (MyTouchListener listener : myTouchListeners) {
//            listener.onTouchEvent(ev);
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("tag","执行了onActivityResult");
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }





}
