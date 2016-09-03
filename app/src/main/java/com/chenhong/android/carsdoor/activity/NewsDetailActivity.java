package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.manuelpeinado.fadingactionbar.view.ObservableWebViewWithHeader;

/**
 * Created by Android on 2016/8/11.
 */
public class NewsDetailActivity extends Activity {

    private WebView webView;
    private ImageView imageView;
    private TextView tv_category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_background)
                .headerLayout(R.layout.header)
                .contentLayout(R.layout.activity_news_detail);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
        imageView= (ImageView) findViewById(R.id.image_header);
        tv_category= (TextView) findViewById(R.id.tv_category);

       if(!TextUtils.isEmpty(getIntent().getStringExtra("title"))){
           getActionBar().setTitle(getIntent().getStringExtra("title"));
           imageView.setImageResource(R.drawable.message__ic_theme3);
           tv_category.setText("新车资讯");
       }
        if(!TextUtils.isEmpty(getIntent().getStringExtra("trytitle"))){
            getActionBar().setTitle(getIntent().getStringExtra("trytitle"));
            imageView.setImageResource(R.drawable.ny);
            tv_category.setText("试车视频");
        }


        webView= (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              return false;
            }
        });







    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

//    @Override
//    protected void initView() {
//
//
//
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(getIntent().getStringExtra("url"));
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                    return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
////                if(url!=null){
////
////
////                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
////
////                    view.loadUrl(fun);
////
////                    String fun2="javascript:function hideOther() {getClass(document,'nav-sides')[0].style.display='none';}";
////
////                    view.loadUrl(fun2);
////
////                    view.loadUrl("javascript:hideOther();");
////
////
////                }
//                super.onPageFinished(view, url);
//            }
//        });
//
//
//    }

//    @Override
//    public int getLayoutID() {
//        return R.layout.activity_news_detail;
//    }





    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
