package com.chenhong.android.carsdoor.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.ActivityStack;
import com.chenhong.android.carsdoor.utils.BitmapUtils;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.MyPhotoDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/8/14.
 */
public class UpdateSelfActivity extends BaseActivity {
    @ViewInject(R.id.rl_icon)
    private RelativeLayout rl_icon;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.rl_nickname)
    private RelativeLayout rl_nickname;
    @ViewInject(R.id.rl_location)
    private RelativeLayout rl_location;
    /** 指定拍摄图片文件位置避免获取到缩略图 */
    private File outFile;

    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.city)
    private  TextView tv_city;

    @ViewInject(R.id.tv_nick)
    private TextView tv_nick;

    @ViewInject(R.id.btn_unregister)
    private Button btn_unregister;


    @ViewInject(R.id.iv_icon)
    private CircleImageView iv_icon;
    private int defaultSelectedValue=0;

    private static final int REQUESTCODE_PICK = 1;
    /** 裁剪好头像-设置头像 */
    private static final int REQUESTCODE_CUTTING = 2;
    /** 选择头像拍照选取 */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;

    /** 裁剪好的头像的Bitmap */
    private Bitmap currentBitmap;
    private String sex;

    @Override
    protected void initView() {

        if((String)BmobUser.getObjectByKey("sex")!=null){
            tv_sex.setText((String)BmobUser.getObjectByKey("sex"));
        }

        if((String)BmobUser.getObjectByKey("icon")!=null){
            ImageLoader.getInstance().displayImage((String)BmobUser.getObjectByKey("icon"),iv_icon);
        }

        if((String)BmobUser.getObjectByKey("nick")!=null){
           tv_nick.setText((String)BmobUser.getObjectByKey("nick"));
        }

        if((String)BmobUser.getObjectByKey("city")!=null){
            tv_city.setText((String)BmobUser.getObjectByKey("city"));
        }

        if(BmobUser.getCurrentUser(_User.class)!=null){
            btn_unregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobUser.logOut();   //清除缓存用户对象
                    ActivityStack.getInstace().finishActivity(UpdateSelfActivity.class);
                    ActivityStack.getInstace().finishActivity(SelfDetailActivity.class);
                    setResult(Constant.REGISTER_LOGIN);
                }
            });
        }

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_updateself;
    }










    @OnClick({R.id.rl_icon,R.id.rl_sex,R.id.rl_nickname,R.id.rl_location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_icon:
            //自定义仿ios   dialog
                new MyPhotoDialog(UpdateSelfActivity.this).builder().addSheetItem("拍照", MyPhotoDialog.SheetItemColor.BULE, new MyPhotoDialog.OnSheetItemClickListener(){

                    @Override
                    public void onClick(int witch) {
                        takephoto();
                    }
                }).addSheetItem("打开相册", MyPhotoDialog.SheetItemColor.BULE, new MyPhotoDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int witch) {
                          openAlbum();
                    }
                }).show();

                break;
            case R.id.rl_sex:
                AlertDialog dialog = new AlertDialog.Builder(UpdateSelfActivity.this)
                        .setSingleChoiceItems(new String[]{"男", "女"}, defaultSelectedValue,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (which == 0) {
                                            sex = "男";
                                            defaultSelectedValue=0;
                                            tv_sex.setText("男");
                                        } else {
                                            sex = "女";
                                            tv_sex.setText("女");
                                            defaultSelectedValue=1;
                                        }
                                        _User user = new _User();
                                        user.setSex(sex);
                                        _User currentUser = BmobUser.getCurrentUser(_User.class);
                                        user.update(currentUser.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
//                                                    dialog.dismiss();
                                                } else {
                                                    Log.e("tazzz", e.getMessage());
//                                                    dialog.dismiss();
                                                }
                                            }
                                        });

                                    }
                                }
                        ).create();
                dialog.show();

                break;
            case R.id.rl_nickname:
               Intent intent=new Intent(UpdateSelfActivity.this,UpdateNickNameActivity.class);
                startActivityForResult(intent, Constant.QUESTION_UPDATE_NICKNAME);
                break;
            case R.id.rl_location:
                Intent intent1=new Intent(UpdateSelfActivity.this,ChooseCityActivity.class);
                startActivityForResult(intent1,Constant.PERSON_UPDATE_LOCATION);
                break;
             default:


                 break;
        }



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if((String)BmobUser.getObjectByKey("sex")!=null){
            tv_sex.setText((String)BmobUser.getObjectByKey("sex"));
        }

        if((String)BmobUser.getObjectByKey("icon")!=null){
            ImageLoader.getInstance().displayImage((String)BmobUser.getObjectByKey("icon"),iv_icon);
        }
        if((String)BmobUser.getObjectByKey("city")!=null){
            tv_city.setText((String)BmobUser.getObjectByKey("city"));
        }

    }

    private void takephoto(){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }


    private void openAlbum(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  进行判断是那个操作跳转回来的，如果是裁剪跳转回来的这块就要把图片现实到View上，其他两种的话都把数据带入裁剪界面
        switch (requestCode) {
            //相册
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            //裁剪
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            //拍照
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));
                break;
            case Constant.QUESTION_UPDATE_NICKNAME:
                if(!TextUtils.isEmpty((String)BmobUser.getObjectByKey("nick"))){
                tv_nick.setText((String)BmobUser.getObjectByKey("nick"));
            }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 调用系统的图片裁剪
     * @param data
     */
    private void startPhotoZoom(Uri data) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);

    }

    /**
     * 把裁剪好的图片设置到View上或者上传到网络
     * @param data
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();

        if (extras != null) {
            Log.e("tazzz","extras!=null");
            /** 可用于图像上传 */
            currentBitmap = extras.getParcelable("data");
            File file= null;
            try {
                file = BitmapUtils.saveBitmap(currentBitmap,"icon.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(file!=null){
                Log.e("tazzz","file!=null");
                //上传
                final BmobFile bmobFile=new BmobFile(file);
                final ProgressDialog progressDialog = new ProgressDialog(UpdateSelfActivity.this);
                progressDialog.setMessage("正在上传。。。");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            _User user=new _User();
                            user.setIcon(bmobFile.getFileUrl());
                            _User currentUser = BmobUser.getCurrentUser(_User.class);
                            user.update(currentUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        MyUtils.showToast(UpdateSelfActivity.this,"上传文件成功");
                                        progressDialog.dismiss();
                                        if((String)BmobUser.getObjectByKey("icon")!=null){
                                            ImageLoader.getInstance().displayImage((String)BmobUser.getObjectByKey("sex"),iv_icon);
                                        }
                                    }else {
                                        Log.e("tazzz",e.getMessage());
                                        MyUtils.showToast(UpdateSelfActivity.this,"上传文件失败");
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        }else{
                            Log.e("tazzz",e.getMessage());
                            MyUtils.showToast(UpdateSelfActivity.this,"上传文件失败");
                            progressDialog.dismiss();
                        }
                    }
                });
            }



        }
    }
}
