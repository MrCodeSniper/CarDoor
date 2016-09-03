package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.entity.car_logo;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Android on 2016/8/5.
 */
public class QuestionsAdapter extends SimpleBaseAdapter<Questions>{

    private Context context;
    private List<Questions> questionses=new ArrayList<Questions>();

    public QuestionsAdapter(Context context, List<Questions> datalist) {
        super(context, datalist);
        this.context=context;
        this.questionses=datalist;
    }

    @Override
public View getView(int position, View convertView, ViewGroup arg2) {
        if(convertView==null){
        convertView=View.inflate(context,R.layout.fragment_question_undo_item, null);
        }
        ViewHolder holder=ViewHolder.getHolder(convertView);
        if(!questionses.get(position).isDone()){
        ImageLoader.getInstance().displayImage(questionses.get(position).getUserIcon().getFileUrl(), holder.iv_account, Constant.options);
        holder.tv_ques_content.setText(questionses.get(position).getQuestionContent());
        holder.tv_anser_count.setText(questionses.get(position).getAnswerCount()+"人回答");
//
        String senddate=questionses.get(position).getSendTime();
            String sendate = MyUtils.formatDuring(new Date(Long.valueOf(senddate)));
            holder.tv_sendtimeandcity.setText(sendate+"  来自  "+questionses.get(position).getCity());
        holder.tv_username.setText(questionses.get(position).getUserName());
        holder.tv_ques_title.setText(questionses.get(position).getQuestionTitle());
        }
        return convertView;
    }

    static class ViewHolder {
        // 列表头布局
        public TextView tv_ques_content;
        // 列表头文字
        public TextView tv_username;
        // 列表车系图片
        public ImageView iv_account;
        // 列表内容名字
        public TextView tv_sendtimeandcity;
        // 列表内容价格
        public TextView tv_ques_title;
        // 在构造方法中封装findviewByid
        public TextView tv_anser_count;


        public ViewHolder(View convertView) {
            tv_anser_count = (TextView) convertView.findViewById(R.id.tv_answer_count);
            tv_ques_content = (TextView) convertView.findViewById(R.id.tv_ques_content);
            iv_account = (ImageView) convertView.findViewById(R.id.iv_account);
            tv_ques_title = (TextView) convertView.findViewById(R.id.tv_ques_title);
            tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            tv_sendtimeandcity = (TextView) convertView.findViewById(R.id.tv_sendtimeandcity);
        }

        public static ViewHolder getHolder(View convertview) {
            ViewHolder holder = (ViewHolder) convertview.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertview);
                convertview.setTag(holder);
            }
            return holder;
        }
    }

        }



