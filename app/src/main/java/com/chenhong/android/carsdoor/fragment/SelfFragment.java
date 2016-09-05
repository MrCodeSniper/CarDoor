package com.chenhong.android.carsdoor.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.LoginActivity;
import com.chenhong.android.carsdoor.activity.SelfDetailActivity;
import com.chenhong.android.carsdoor.activity.SettingActivity;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.view.parallax.ParallaxScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.bmob.v3.BmobUser;


/**
 * 我的
 * 
 * @author blue
 */
public class SelfFragment extends BaseFragment {
	@ViewInject(R.id.scrollView1)
	ParallaxScrollView parallax;
	@ViewInject(R.id.imageView1)
	ImageView image;
	@ViewInject(R.id.tv_denglu)
	private TextView tv_denglu;
	@ViewInject(R.id.tv_city)
	private TextView tv_city;
	@ViewInject(R.id.iv_account)
	private ImageView iv_account;
	@ViewInject(R.id.rl_setting)
	private RelativeLayout rl_setting;


	@ViewInject(R.id.ll_login)
	private RelativeLayout rl_login;

	private SharedPreferences sp;

	private boolean isLogin = false;


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_login_main;
	}

	@Override
	protected void initParams() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// create our manager instance after the content view is set
			SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
			// enable status bar tint
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarAlpha(0.0f);
		}

		parallax.setImageViewToParallax(image);
		sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);

//		if(BmobUser.getCurrentUser(_User.class)==null){
//			rl_login.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					Intent intent=new Intent(getActivity(), LoginActivity.class);
//					startActivityForResult(intent, Constant.LOGIN_REQUEST);
//				}
//			});
//
//		}else {
//			rl_login.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent intent=new Intent(getActivity(), SelfDetailActivity.class);
//					startActivityForResult(intent, Constant.LOGIN_MID);
//				}
//			});
//			if(TextUtils.isEmpty((String) BmobUser.getObjectByKey("nick"))||(String) BmobUser.getObjectByKey("nick")==null){
//				tv_denglu.setText((String)BmobUser.getObjectByKey("username"));
//			}else {
//				tv_denglu.setText((String)BmobUser.getObjectByKey("nick"));
//			}
////			tv_city.setText("广州市");
//			if((String)BmobUser.getObjectByKey("icon")==null){
//				iv_account.setImageResource(R.drawable.bj__dna_result_avatar_male);
//			}else {
//				ImageLoader.getInstance().displayImage(((String) BmobUser.getObjectByKey("icon")),iv_account);
//			}
//			if(TextUtils.isEmpty((String) BmobUser.getObjectByKey("city"))||(String) BmobUser.getObjectByKey("city")==null){
//				tv_city.setText("请设置所在城市");
//			}else {
//				tv_city.setText((String) BmobUser.getObjectByKey("city"));
//			}
//	}
		rl_setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);
			}
		});



	}


	private Fragment getRootFragment() {
		Fragment fragment = getParentFragment();
		while (fragment.getParentFragment() != null) {
			fragment = fragment.getParentFragment();
		}
		return fragment;

	}

	@Override
	protected void initLoading() {

	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//			case Constant.LOGIN_REQUEST:
////				if(BmobUser.getCurrentUser(_User.class)!=null){
////					tv_denglu.setText(data.getExtras().getString("username"));
////					rl_login.setClickable(false);
////					tv_city.setText("广州市");
////					iv_account.setImageResource(R.drawable.benchi);
////				}
//
//			break;
//
//			default:
//
//				break;
//
//
//		}

		Log.e("tag","执行了onActivityResult");
		if(resultCode==Constant.REGISTER_LOGIN){
			iv_account.setImageResource(R.drawable.bj__me_fragment_user_avatar_default);
			tv_denglu.setText("立即登录");
			tv_city.setText("登录尊享更多购车特权");
			rl_login.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(getActivity(), LoginActivity.class);
					startActivityForResult(intent, Constant.LOGIN_REQUEST);
				}
			});
		}


	}





	@Override
	public void onResume() {
		super.onResume();

			if(BmobUser.getCurrentUser(_User.class)!=null) {


				if (TextUtils.isEmpty((String) BmobUser.getObjectByKey("nick")) || (String) BmobUser.getObjectByKey("nick") == null) {
					tv_denglu.setText((String) BmobUser.getObjectByKey("username"));
				} else {
					tv_denglu.setText((String) BmobUser.getObjectByKey("nick"));
				}


				rl_login.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), SelfDetailActivity.class);
						startActivity(intent);
					}
				});


				if (TextUtils.isEmpty((String) BmobUser.getObjectByKey("city")) || (String) BmobUser.getObjectByKey("city") == null) {
					tv_city.setText("请设置所在城市");
				} else {
					tv_city.setText((String) BmobUser.getObjectByKey("city"));
				}
				if ((String) BmobUser.getObjectByKey("icon") == null) {
					iv_account.setImageResource(R.drawable.bj__dna_result_avatar_male);
				} else {
					ImageLoader.getInstance().displayImage(((String) BmobUser.getObjectByKey("icon")), iv_account);
				}
//				if((BmobFile)BmobUser.getObjectByKey("Theme")==null){
//					image.setImageResource(R.drawable.message__ic_theme1);
//				}else {
//				ImageLoader.getInstance().displayImage(( BmobUser.getObjectByKey("Theme")).getFileUrl(), iv_account);
		//	}

			}else {
                tv_city.setText("登录尊享更多购车特权");
				tv_denglu.setText("立即登录");
				iv_account.setImageResource(R.drawable.bj__me_fragment_user_avatar_default);
				rl_login.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent=new Intent(getActivity(), LoginActivity.class);
						startActivityForResult(intent, Constant.LOGIN_REQUEST);
					}
				});
			}



	}
}
