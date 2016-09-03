package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.SearchCar;
import com.chenhong.android.carsdoor.entity.car_series;
import com.chenhong.android.carsdoor.global.Constant;
import com.hp.hpl.sparta.Text;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/8/24.
 */
public class FilterAdapter extends SimpleBaseAdapter<SearchCar>{

    private Context context;
    private List<SearchCar> datas=new ArrayList<SearchCar>();


    public FilterAdapter(Context context, List<SearchCar> datalist) {
        super(context, datalist);
        this.context=context;
        this.datas=datalist;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup arg2) {
         ViewHolder holder;
        if(convertview==null){
            convertview=View.inflate(context, R.layout.filter_item,null);
            holder=new ViewHolder();
            holder.iv_title= (ImageView) convertview.findViewById(R.id.iv_title);
            holder.tv_price= (TextView) convertview.findViewById(R.id.tv_price);
            holder.tv_title=(TextView) convertview.findViewById(R.id.tv_name);
            convertview.setTag(holder);
        }else {
            holder= (ViewHolder) convertview.getTag();
        }
        holder.tv_price.setText(datas.get(position).getPrice());
        holder.tv_title.setText(datas.get(position).getName());
        ImageLoader.getInstance().displayImage(datas.get(position).getIcon().getFileUrl(),holder.iv_title, Constant.options);
        return convertview;
    }





    static class ViewHolder{
        ImageView iv_title;
        TextView tv_title;
        TextView tv_price;
    }





}
