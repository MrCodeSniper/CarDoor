package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.NewsBuy;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/8/16.
 */
public class NewsBuyAdapter extends SimpleBaseAdapter<NewsBuy> {


    private Context context;
    private List<NewsBuy> list=new ArrayList<>();

    public NewsBuyAdapter(Context context, List<NewsBuy> datalist) {
        super(context, datalist);
        this.context=context;
        this.list=datalist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.news_item,null);
        }
        final ViewHolder viewHolder=ViewHolder.getHolder(convertView);
        viewHolder.tv_title.setText(list.get(position).getTitle());

        if(position>=0&&position<=30){
            String url = list.get(position).getTitleImage().getFileUrl();
            ImageLoader.getInstance().displayImage(url, viewHolder.iv_title, Constant.options);
        }else {
            viewHolder.iv_title.setImageResource(R.drawable.empty_photo);

        }
        return convertView;
    }


    static class ViewHolder{
        TextView tv_title;
        ImageView iv_title;
        public ViewHolder(View convertview){
            tv_title= (TextView) convertview.findViewById(R.id.item_tv_title);
            iv_title= (ImageView) convertview.findViewById(R.id.item_iv_img);
        }
        public  static ViewHolder getHolder(View convertview){
            ViewHolder viewHolder= (ViewHolder) convertview.getTag();
            if(viewHolder==null){
                viewHolder=new ViewHolder(convertview);
                convertview.setTag(viewHolder);
            }
            return  viewHolder;
        }
    }
}
