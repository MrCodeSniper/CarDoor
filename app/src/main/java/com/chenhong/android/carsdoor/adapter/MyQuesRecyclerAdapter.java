package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android on 2016/8/25.
 */
public class MyQuesRecyclerAdapter extends RecyclerView.Adapter{


    private List<Questions> questionses;
    private Context mContext;
    private LayoutInflater inflater;

    public MyQuesRecyclerAdapter(Context context, List<Questions> datas) {
        this. mContext=context;
        this. questionses=datas;
        inflater=LayoutInflater. from(mContext);
    }



    @Override
    public int getItemCount() {

        return questionses.size();
    }



    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_price,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1= (MyViewHolder) holder;
        if(!questionses.get(position).isDone()){
            ImageLoader.getInstance().displayImage(questionses.get(position).getUserIcon().getFileUrl(), holder1.iv_account, Constant.options);
            holder1.tv_ques_content.setText(questionses.get(position).getQuestionContent());
            holder1.tv_anser_count.setText(questionses.get(position).getAnswerCount()+"人回答");
//
            String senddate=questionses.get(position).getSendTime();
            String sendate = MyUtils.formatDuring(new Date(Long.valueOf(senddate)));
            holder1.tv_sendtimeandcity.setText(sendate+"  来自  "+questionses.get(position).getCity());
            holder1.tv_username.setText(questionses.get(position).getUserName());
            holder1.tv_ques_title.setText(questionses.get(position).getQuestionTitle());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        // 列表头布局
        public TextView tv_ques_content;
        // 列表头文字
        public TextView tv_username;
        // 列表车系图片
        public CircleImageView iv_account;
        // 列表内容名字
        public TextView tv_sendtimeandcity;
        // 列表内容价格
        public TextView tv_ques_title;
        // 在构造方法中封装findviewByid
        public TextView tv_anser_count;

        public MyViewHolder(View convertView) {
            super(convertView);
            tv_anser_count = (TextView) convertView.findViewById(R.id.tv_answer_count);
            tv_ques_content = (TextView) convertView.findViewById(R.id.tv_ques_content);
            iv_account = (CircleImageView) convertView.findViewById(R.id.iv_account);
            tv_ques_title = (TextView) convertView.findViewById(R.id.tv_ques_title);
            tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            tv_sendtimeandcity = (TextView) convertView.findViewById(R.id.tv_sendtimeandcity);
        }

    }



}
