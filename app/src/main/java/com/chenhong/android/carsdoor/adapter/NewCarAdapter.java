package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.NewCar;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/8/11.
 */
public class NewCarAdapter extends SimpleBaseAdapter<NewCar>{
    private Context context;
    private List<NewCar> datalist=new ArrayList<>();
    public NewCarAdapter(Context context, List<NewCar> datalist) {
        super(context, datalist);
        this.context=context;
        this.datalist=datalist;
    }


    final int TYPE_1 = 0;
    final int TYPE_2 = 1;




    //每个convert view都会调用此方法，获得当前所需要的view样式


    @Override
    public int getItemViewType(int position) {
        int p = datalist.get(position).getType();
        if(p == 0)
        return TYPE_1;
        else
        return TYPE_2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
            NewCar newCar = datalist.get(position);

            ViewHolder viewHolder = null;
            ViewHolder2 viewHolder2 = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case TYPE_1:
                        convertView = LayoutInflater.from(context).inflate(R.layout.newcar_item_one, null);
                        viewHolder = new ViewHolder();
                        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_newcar_title);
                        viewHolder.iv_title = (ImageView) convertView.findViewById(R.id.iv_newcarpic);
                        viewHolder.tv_newcar_from = (TextView) convertView.findViewById(R.id.tv_newcar_from);
                        viewHolder.tv_newcar_time = (TextView) convertView.findViewById(R.id.tv_newcar_time);
                        convertView.setTag(viewHolder);
                        break;
                    case TYPE_2:
                        convertView = LayoutInflater.from(context).inflate(R.layout.newcar_item_two, null);
                        viewHolder2 = new ViewHolder2();
                        viewHolder2.tv_title = (TextView) convertView.findViewById(R.id.tv_newcar_title);
                        viewHolder2.iv_title = (ImageView) convertView.findViewById(R.id.iv_newcarpic);
                        viewHolder2.iv_pic2 = (ImageView) convertView.findViewById(R.id.iv_newcarpic2);
                        viewHolder2.iv_pic3 = (ImageView) convertView.findViewById(R.id.iv_newcarpic3);
                        viewHolder2.tv_newcar_from = (TextView) convertView.findViewById(R.id.tv_newcar_from);
                        viewHolder2.tv_newcar_time = (TextView) convertView.findViewById(R.id.tv_newcar_time);
                        convertView.setTag(viewHolder2);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {

                    case TYPE_1:
                        viewHolder = (ViewHolder) convertView.getTag();
                        Log.e("convertView !!!!!!= ", "NULL TYPE_1");
                        break;
                    case TYPE_2:
                        viewHolder2 = (ViewHolder2) convertView.getTag();
                        Log.e("convertView !!!!!!= ", "NULL TYPE_2");
                        break;

                }

            }

            switch (type) {
                case TYPE_1:
                    viewHolder.tv_title.setText(newCar.getNewCarTitle());
                    viewHolder.tv_newcar_from.setText(newCar.getFrom());
                    ImageLoader.getInstance().displayImage(newCar.getTitlePic().getFileUrl(), viewHolder.iv_title, Constant.options);
                    break;
                case TYPE_2:
                    viewHolder2.tv_title.setText(newCar.getNewCarTitle());
                    viewHolder2.tv_newcar_from.setText(newCar.getFrom());
                    ImageLoader.getInstance().displayImage(newCar.getTitlePic().getFileUrl(), viewHolder2.iv_title, Constant.options);
                    ImageLoader.getInstance().displayImage(newCar.getTitlePic2().getFileUrl(), viewHolder2.iv_pic2, Constant.options);
                    ImageLoader.getInstance().displayImage(newCar.getTitlePic3().getFileUrl(), viewHolder2.iv_pic3, Constant.options);
                    break;
            }

        return convertView;
    }



    class ViewHolder{
        TextView tv_title;
        ImageView iv_title;
        TextView tv_newcar_from;
        TextView tv_newcar_time;

    }

    class ViewHolder2{
        TextView tv_title;
        ImageView iv_title;
        ImageView iv_pic2;
        ImageView iv_pic3;
        TextView tv_newcar_from;
        TextView tv_newcar_time;
    }



























































}
