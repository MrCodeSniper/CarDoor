package com.chenhong.android.carsdoor.global;

import android.graphics.Bitmap;

import com.chenhong.android.carsdoor.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class Constant {

	public static final int NEWSBUY=0;
	public static final int NEWSCAR=1;
	public static final int NEWSIMP=2;
	public static final int NEWSMAR=3;
	public static final int NEWSPIC=4;
	public static final int NEWSTRY=5;
	public static final  int SCROLLMESSAGE=6;
	public static final  int QUESTION_LOGIN_REQUEST=8;
	public static final  int QUESTION_SEND_REQUEST=11;
	public static final  int QUESTION_UPDATE_NICKNAME=12;
	public static final  int PERSON_UPDATE_LOCATION=13;
	public static final  int LOGIN_MID=7;
	public static final  int LOGIN_REQUEST=14;
	public static final  int REGISTER_LOGIN=13;
	public static final int LOGIN_ONE=9;
	public static final int LOGIN_TWO=10;


	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.empty_photo)
			.showImageOnFail(R.drawable.empty_photo)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	public static final String APPKEY ="55b0557625f51a6d6567661fe8e9ea17";
}
