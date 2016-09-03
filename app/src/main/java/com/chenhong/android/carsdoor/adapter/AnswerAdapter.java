package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Answer;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.entity.car_logo;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Android on 2016/8/29.
 */
public class AnswerAdapter extends SimpleBaseAdapter<Answer> {

    private Context context;
    private List<Answer> logos=new ArrayList<Answer>();


    public AnswerAdapter(Context context, List<Answer> datalist) {
        super(context, datalist);
        this.context=context;
        this.logos=datalist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.answer_item, null);
        }
        final ViewHolder holder=ViewHolder.getHolder(convertView);
        if(logos.get(position).getAid()==-1){

        }else {
            holder.tv_shafa.setText("沙发");
            holder.ll_no_comment.setVisibility(View.GONE);
            holder.tv_answer.setText(logos.get(position).getAnswer());
            final String[] split = logos.get(position).getCreatedAt().split(" ");
            BmobQuery<_User> query = new BmobQuery<_User>();
            query.getObject(logos.get(position).getUser().getObjectId(), new QueryListener<_User>() {
                @Override
                public void done(_User object, BmobException e) {
                    if(e==null){
                        holder.tv_name.setText(object.getUsername());
                        holder.tv_sendtimeandcity.setText(split[1]+"  来自  "+ object.getCity());
                        ImageLoader.getInstance().displayImage(object.getIcon(),holder.iv_logo, Constant.options);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }








        return convertView;
    }

    static class ViewHolder{
        LinearLayout ll_no_comment;
        TextView tv_sendtimeandcity;
        ImageView iv_logo;
        TextView tv_name;
        TextView tv_shafa;
        TextView tv_answer;
        // 在构造方法中封装findviewByid
        public ViewHolder(View convertview){
            tv_shafa= (TextView) convertview.findViewById(R.id.tv_shafa);
            ll_no_comment= (LinearLayout) convertview.findViewById(R.id.ll_no_comment);
            iv_logo= (ImageView) convertview.findViewById(R.id.iv_account);
            tv_name= (TextView) convertview.findViewById(R.id.tv_username);
            tv_answer= (TextView) convertview.findViewById(R.id.tv_answer);
            tv_sendtimeandcity=(TextView) convertview.findViewById(R.id.tv_sendtimeandcity_answer);
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
