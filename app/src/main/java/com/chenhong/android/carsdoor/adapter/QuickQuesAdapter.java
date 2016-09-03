package com.chenhong.android.carsdoor.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickQuesAdapter extends BaseQuickAdapter<Questions> {


    public QuickQuesAdapter(List<Questions> data) {
        super(R.layout.fragment_question_undo_item, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, Questions questions) {
        if(!questions.isDone()){


            String senddate=questions.getCreatedAt().split(" ")[1];
            helper.setText(R.id.tv_answer_count, questions.getAnswerCount()+"人回答")
                    .setText(R.id.tv_ques_content, questions.getQuestionContent())
                    .setText(R.id.tv_ques_title,questions.getQuestionTitle())
                    .setText(R.id.tv_username,questions.getUserName())
                    .setText(R.id.tv_sendtimeandcity,senddate+"  来自  "+questions.getCity());

            ImageLoader.getInstance().displayImage(questions.getUserImage(),(ImageView) helper.getView(R.id.iv_account), Constant.options);
            ImageLoader.getInstance().displayImage(questions.getUserTheme(),(ImageView) helper.getView(R.id.iv_theme), Constant.options);
        }
    }



}
