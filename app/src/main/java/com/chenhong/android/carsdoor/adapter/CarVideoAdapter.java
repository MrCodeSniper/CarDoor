package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.NewsDetailActivity;
import com.chenhong.android.carsdoor.entity.NewCar;
import com.chenhong.android.carsdoor.entity.NewsVideo;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/8/16.
 */
public class CarVideoAdapter extends SimpleBaseAdapter<NewsVideo> {


    private Context context;
    private List<NewsVideo> videos=new ArrayList<>();

    public CarVideoAdapter(Context context, List<NewsVideo> datalist) {
        super(context, datalist);
        this.context=context;
        this.videos=datalist;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup arg2) {
//        ViewHolder holder;
//        if(convertview==null){
//            convertview=View.inflate(context, R.layout.griditem,null);
//            holder=new ViewHolder();
//            holder.ll_all= (LinearLayout) convertview.findViewById(R.id.ll_all);
//            holder.tv_title= (TextView) convertview.findViewById(R.id.tv_title);
//            holder.iv_title= (ImageView) convertview.findViewById(R.id.iv_title);
//            holder.tv_num= (TextView) convertview.findViewById(R.id.tv_num);
//            convertview.setTag(holder);
//        }else {
//            holder = (ViewHolder)convertview.getTag();
//        }
//        holder.tv_title.setText(videos.get(position).getTitle());
//        holder.tv_num.setText(videos.get(position).getPlaycount());
//        holder.ll_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, NewsDetailActivity.class);
//                Log.e("tazzz",videos.get(position).getTitle());
//                intent.putExtra("url",videos.get(position).getWebsite());
//                intent.putExtra("trytitle",videos.get(position).getTitle());
//                context.startActivity(intent);
//            }
//        });
//            String url = videos.get(position).getImagetitle().getFileUrl();
//            ImageLoader.getInstance().displayImage(url, holder.iv_title, Constant.options);
//        return convertview;







        View view=View.inflate(context, R.layout.griditem,null);
       LinearLayout ll_all= (LinearLayout) view.findViewById(R.id.ll_all);
       TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
       ImageView iv_title= (ImageView) view.findViewById(R.id.iv_title);
       TextView tv_num= (TextView) view.findViewById(R.id.tv_num);

       tv_title.setText(videos.get(position).getTitle());
        tv_num.setText(videos.get(position).getPlaycount());
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsDetailActivity.class);
                Log.e("tazzz",videos.get(position).getTitle());
                intent.putExtra("url",videos.get(position).getWebsite());
                Log.e("taqqq",videos.get(position).getTitle());
                intent.putExtra("trytitle",videos.get(position).getTitle());
                context.startActivity(intent);
            }
        });
        String url = videos.get(position).getImagetitle().getFileUrl();
        ImageLoader.getInstance().displayImage(url, iv_title, Constant.options);


     return view;
    }



//    static class ViewHolder {
//        TextView tv_title;
//        ImageView iv_title;
//        TextView tv_num;
//        LinearLayout ll_all;
//
//    }



}
