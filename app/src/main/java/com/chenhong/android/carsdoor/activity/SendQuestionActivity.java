package com.chenhong.android.carsdoor.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2016/8/7.
 */
public class SendQuestionActivity extends BaseActivity{

    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.et_title)
    private EditText et_title;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    private SharedPreferences sp;
    SpotsDialog spotsDialog;






    @Override
    protected void initView() {

         sp=getSharedPreferences("config",MODE_PRIVATE);



    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            //TODO
//            return true;
//        }
//        return false;
//    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_sendques;
    }

    @OnClick({R.id.et_content,R.id.et_title,R.id.tv_send})
    public void onclick(View view){

        switch (view.getId()){
            case R.id.et_content:


                break;
            case R.id.et_title:


                break;
            case R.id.tv_send:
                if(TextUtils.isEmpty(et_content.getText().toString())||TextUtils.isEmpty(et_title.getText().toString())){
                    break;
                }
                spotsDialog = new SpotsDialog(this);
                spotsDialog.show();
                _User user=BmobUser.getCurrentUser(_User.class);
                final Questions questions=new Questions();
                questions.setCity(user.getCity());
                questions.setAnswerCount(0);
                questions.setIsDone(false);
                questions.setQuestionContent(et_content.getText().toString());
                questions.setUserName(user.getNick());
                questions.setQuestionTitle(et_title.getText().toString());
                questions.setUserImage(user.getIcon());
                questions.setUserTheme(user.getTheme().getFileUrl());
                questions.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Log.i("bmb","创建数据成功");
                                        Intent intent=new Intent();
                                        intent.putExtra("isSendSuccess",true);
                                        setResult(Constant.QUESTION_SEND_REQUEST,intent);
                                        spotsDialog.dismiss();
                                        MyUtils.showToast(SendQuestionActivity.this,"创建帖子成功");
                                        finish();
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                        MyUtils.showToast(SendQuestionActivity.this,"创建帖子失败");
                                        spotsDialog.dismiss();
                                    }
                                }
                            });
                        }




        }







    }










