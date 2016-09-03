package com.chenhong.android.carsdoor.adapter;

import android.view.View;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickAdapter extends BaseQuickAdapter<String> {


    public QuickAdapter(List<String> mDatas) {
        super( R.layout.item_price, mDatas);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item, item);
        MyUtils.displayFromDrawable(R.drawable.shop,(ImageView) helper.getView(R.id.iv));
    }


    @Override
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        super.setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener);
    }
}
