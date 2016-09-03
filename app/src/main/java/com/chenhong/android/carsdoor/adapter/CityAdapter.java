package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.City;
import com.chenhong.android.carsdoor.entity.car_logo;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/7/27.
 */
public class CityAdapter extends SimpleBaseAdapter<City> {

    private Context context;
    private List<City> logos=new ArrayList<>();



    public CityAdapter(Context context, List<City> datalist) {
        super(context, datalist);
        this.context=context;
        this.logos=datalist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.city_item, null);
        }
        ViewHolder holder= ViewHolder.getHolder(convertView);
        String name=logos.get(position).getCityName();
        String letter=logos.get(position).getNameSort().charAt(0)+"";
        if(position>0){
            String lastletter=logos.get(position-1).getNameSort().charAt(0)+"";
            if(letter.equals(lastletter)){
                holder.tv_letter.setVisibility(View.GONE);
            }else {
                //布局是复用的要再次设置布局可见
                holder.tv_letter.setVisibility(View.VISIBLE);
                holder.tv_letter.setText(letter);
            }
        }else {
            holder.tv_letter.setVisibility(View.VISIBLE);
            holder.tv_letter.setText(letter);
        }
        holder.tv_name.setText(name);
        return convertView;
    }
    static class ViewHolder{
        TextView tv_letter;
        TextView tv_name;
        // 在构造方法中封装findviewByid
        public ViewHolder(View convertview){
            tv_name= (TextView) convertview.findViewById(R.id.list_item_content_text);
            tv_letter= (TextView) convertview.findViewById(R.id.tv_letter);
        }
        public static ViewHolder getHolder(View convertview){
            ViewHolder holder= (ViewHolder) convertview.getTag();
            if(holder==null){
                holder=new ViewHolder(convertview);
                convertview.setTag(holder);
            }
            return holder;
        }
    }

}
