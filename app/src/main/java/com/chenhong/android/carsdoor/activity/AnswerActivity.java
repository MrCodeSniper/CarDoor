package com.chenhong.android.carsdoor.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.AnswerAdapter;
import com.chenhong.android.carsdoor.entity.Answer;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.parallax.ParallaxScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

/**
 * Created by Android on 2016/8/29.
 */
public class AnswerActivity extends BaseActivity {
    @ViewInject(R.id.lv_answer)
    private ListView lv_answer;

//    private TextView tv_answer_count;

    private TextView tv_ques_title;

    private TextView tv_ques_content;

    private TextView tv_sendtimeandcity;

    private TextView tv_username;

    private ImageView iv_account;
    @ViewInject(R.id.iv_theme)
    private ImageView iv_theme;
    @ViewInject(R.id.scrollView1)
    ParallaxScrollView parallax;
    private AnswerAdapter adapter;
    private AnswerAdapter adapter2;
    private Questions question;
    private SpotsDialog spotsDialog;
    private SpotsDialog spotsDialog2;
    @ViewInject(R.id.discuss)
    private EditText et_discuss;
    @ViewInject(R.id.discuss_submit)
    private TextView btn_commit;


    private List<Answer> answers=new ArrayList<>();


    @Override
    protected void initView() {
        parallax.setImageViewToParallax(iv_theme);

        spotsDialog = new SpotsDialog(this);
        spotsDialog.show();
        View view=View.inflate(AnswerActivity.this,R.layout.answerhead,null);
        tv_ques_title= (TextView) view.findViewById(R.id.tv_ques_title);
        tv_sendtimeandcity= (TextView) view.findViewById(R.id.tv_sendtimeandcity);
        tv_username= (TextView) view.findViewById(R.id.tv_username);
        iv_account= (ImageView) view.findViewById(R.id.iv_account);
        tv_ques_content= (TextView) view.findViewById(R.id.tv_ques_content);
//        tv_answer_count= (TextView) view.findViewById(R.id.tv_answer_count);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            question = (Questions) bundle.getSerializable("question");
            Log.e("tazzz", question.getUserName());
//            tv_answer_count.setText(question.getAnswerCount()+"人回答");
            tv_ques_content.setText(question.getQuestionContent());
            tv_ques_title.setText(question.getQuestionTitle());
            String sendate=question.getCreatedAt().split(" ")[1];
            tv_sendtimeandcity.setText(sendate+"  来自  "+ question.getCity());
            tv_username.setText(question.getUserName());

            ImageLoader.getInstance().displayImage(question.getUserImage(),iv_account, Constant.options);
            ImageLoader.getInstance().displayImage(question.getUserTheme(),iv_theme, Constant.options);
        }


        lv_answer.addHeaderView(view);

        final BmobQuery<Answer> query = new BmobQuery<Answer>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        Questions post = new Post();
//        post.setObjectId("ESIt3334");
        query.addWhereEqualTo("question",new BmobPointer(question));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> objects, BmobException e) {
              if(e==null){
                  if(objects.size()>0){
                      answers=objects;
                  }else {
                     Answer answer=new Answer();
                      answer.setAid(-1);
                      answers.add(answer);
                  }
                  if(adapter==null){
                      adapter = new AnswerAdapter(AnswerActivity.this,answers);
                      lv_answer.setAdapter(adapter);
                  }else {
                      adapter.refreshDatas(answers);
                      adapter.notifyDataSetChanged();
                  }
                  spotsDialog.dismiss();
              }else {
                   Log.e("bmob",e.getMessage());
                  spotsDialog.dismiss();
              }
            }
        });


        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_discuss.getText().toString())){
                    MyUtils.showToast(AnswerActivity.this,"评论不能为空");
                    return;
                }else if(BmobUser.getCurrentUser(_User.class)==null){
                    MyUtils.showToast(AnswerActivity.this,"请登录");
                    return;
                }else {
                    spotsDialog2 = new SpotsDialog(AnswerActivity.this);
                    spotsDialog2.show();
        _User user = BmobUser.getCurrentUser(_User.class);
        final Answer answer = new Answer();
        answer.setAnswer(et_discuss.getText().toString());
        answer.setQuestion(new BmobPointer(question));
        answer.setUser(new BmobPointer(user));
        answer.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Log.i("bmob","评论发表成功");
                    MyUtils.showToast(AnswerActivity.this,"评论发表成功");
                    et_discuss.setText("");
                    query();
                }else{
                    MyUtils.showToast(AnswerActivity.this,"评论发表失败");
                    Log.i("bmob","失败："+e.getMessage());
                    et_discuss.setText("");
                    spotsDialog2.dismiss();
                }
            }
        });
                }
            }
        });









    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_answer;
    }

    public void query(){
        Log.e("taqqq","重新查询");
        BmobQuery<Answer> query = new BmobQuery<Answer>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
//        Questions post = new Post();
//        post.setObjectId("ESIt3334");
        query.addWhereEqualTo("question",new BmobPointer(question));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> objects, BmobException e) {
                if(e==null){
                    Log.e("taqqq","没有异常");
                    if(objects.size()>0){
                        answers=objects;
                        Log.e("taqqq","有值"+answers.size());
                    }else {
                        Answer answer=new Answer();
                        answer.setAid(-1);
                        answers.add(answer);
                    }
                        adapter = new AnswerAdapter(AnswerActivity.this,answers);
                        lv_answer.setAdapter(adapter);
                    spotsDialog2.dismiss();
                }else {
                    Log.e("bmob",e.getMessage());
                    spotsDialog2.dismiss();
                }
            }
        });
    }



}
