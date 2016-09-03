package com.chenhong.android.carsdoor.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.FixedEditText.FixedEditText;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Android on 2016/8/15.
 */
public class UpdateNickNameActivity extends BaseActivity {

    @ViewInject(R.id.tv_save)
    private TextView tv_save;
    @ViewInject(R.id.edit_nickname)
    private FixedEditText et_nickname;


    @Override
    protected void initView() {
        et_nickname.setFixedText("   昵称");
        if((String)BmobUser.getObjectByKey("nick")!=null){
            et_nickname.setText((String)BmobUser.getObjectByKey("nick"));
        }
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_nickname.getText().toString())){
                    MyUtils.showToast(UpdateNickNameActivity.this,"昵称不能为空");
                }else if(et_nickname.getText().toString().equals((String)BmobUser.getObjectByKey("nick"))){
                     finish();
                } else {
                    _User user=new _User();
                    user.setNick(et_nickname.getText().toString());
                    _User currentUser = BmobUser.getCurrentUser(_User.class);
                    user.update(currentUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                MyUtils.showToast(UpdateNickNameActivity.this,"保存成功");
                                finish();
                            }else {
                                Log.e("tazzz",e.getMessage());
                                MyUtils.showToast(UpdateNickNameActivity.this,"保存失败");
                            }
                        }
                    });
                }
            }
        });



    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_updatenickname;
    }
}
