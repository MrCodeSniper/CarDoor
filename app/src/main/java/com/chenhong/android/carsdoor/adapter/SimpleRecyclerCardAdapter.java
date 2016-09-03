package com.chenhong.android.carsdoor.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/8/3.
 */
public class SimpleRecyclerCardAdapter extends RecyclerView.Adapter<SimpleCardViewHolder> {

    private Context mCtx;
    private LayoutInflater mInflater;
    private List<String> mDataSource = new ArrayList<String>();
    private OnItemActionListener mOnItemActionListener;


    public SimpleRecyclerCardAdapter(Context mCtx, List<String> dataList) {
        super();
        this.mCtx = mCtx;
        mInflater = LayoutInflater.from(mCtx);
        this.mDataSource.addAll(dataList);
    }

    @Override
    public SimpleCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v =  mInflater.inflate(R.layout.simple_card_item, viewGroup,false);
        SimpleCardViewHolder simpleViewHolder = new SimpleCardViewHolder(v);
        simpleViewHolder.setIsRecyclable(true);

        return simpleViewHolder;
    }

    @Override
    public void onBindViewHolder(final SimpleCardViewHolder viewHolder, int i) {
        ImageLoader.getInstance().displayImage(mDataSource.get(i),viewHolder.ivImage, Constant.options);
        if(mOnItemActionListener!=null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    mOnItemActionListener.onItemClickListener(v,viewHolder.getPosition());
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    return mOnItemActionListener.onItemLongClickListener(v, viewHolder.getPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }


    /**********定义点击事件**********/
    public   interface OnItemActionListener
    {
        public   void onItemClickListener(View v,int pos);
        public   boolean onItemLongClickListener(View v,int pos);
    }
    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}


class SimpleCardViewHolder extends RecyclerView.ViewHolder
{
    public ImageView ivImage;

    public SimpleCardViewHolder(View layout) {
        super(layout);
        ivImage = (ImageView) layout.findViewById(R.id.item_img);
//        int width = ((Activity) ivImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
//        ViewGroup.LayoutParams params = ivImage.getLayoutParams();
//        //设置图片的相对于屏幕的宽高比
//        params.width = width/3;
//        params.height =  (int) (200 + Math.random() * 400) ;
//        ivImage.setLayoutParams(params);
    }
}