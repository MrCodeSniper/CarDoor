package com.chenhong.android.carsdoor.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class FileUtils {

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getDiskCacheDir(Context context){
        String cachePath;
        //�����sd��
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)||!Environment.isExternalStorageRemovable()){//�ⲿ�ڴ治�����Ƴ�״̬
            cachePath=context.getExternalCacheDir().getAbsolutePath();//�õ�sd������·��
        }else {
            cachePath=context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }



    //安装文件，一般固定写法
    public static void update(String version,Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), version)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
	
	
}
