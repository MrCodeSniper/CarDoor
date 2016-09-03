package com.chenhong.android.carsdoor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Android on 2016/8/22.
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    @ViewInject(R.id.tv_search_cancel)
    private TextView tv_search_cacel;

    @ViewInject(R.id.sv)
    private SearchView sv;
    @ViewInject(R.id.lv_search)
    private ListView lv;

    @ViewInject(R.id.tv_search_cancel)
    private TextView tv_serach_cancel;


    //自动完成的列表
    private final String[] mStrings = {"aaaaaa", "bbbbbb", "cccccc"};


    @Override
    protected void initView() {


//        int search_mag_icon_id = sv.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
//        ImageView mSearchViewIcon = (ImageView) sv.findViewById(search_mag_icon_id);// 获取搜索图标
//        mSearchViewIcon.setImageResource(R.drawable.bj__ic_search);
//        mSearchViewIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);


        //设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);

        //为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        sv.setIconifiedByDefault(false);
        //设置该SearchView内默认显示的提示文本
        sv.setQueryHint("您想要查找的车系");
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings));
        lv.setTextFilterEnabled(true);


        tv_search_cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_search;
    }

    //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //用户输入字符时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            //清楚ListView的过滤
            lv.clearTextFilter();
        } else {
            //使用用户输入的内容对ListView的列表项进行过滤
            lv.setFilterText(newText);
        }
        return true;
    }
}
