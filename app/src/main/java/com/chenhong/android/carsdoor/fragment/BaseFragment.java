package com.chenhong.android.carsdoor.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * basefragment
 * 
 * @author blue
 */
public abstract class BaseFragment extends Fragment {
	private Context context;
	private boolean isLoading=false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}




	private View mView;//缓存Fragment view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null && getActivity() != null)//如果是fragment第一次初始化
		{
			mView = inflater.inflate(getLayoutId(), container, false);
			if (savedInstanceState != null)
				onRestoreInstanceState(savedInstanceState);
			ViewUtils.inject(this, mView);
			initParams();
		}
		 else if (mView != null)
		{
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null)
			{
				Log.e("tazzz","remove了parent");
				parent.removeView(mView);
			}else {
				Log.e("tazzz","没有removeparent");
			}

		}
		return mView;
	}

	protected abstract int getLayoutId();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected abstract void initParams();

	protected abstract void initLoading();
	@Override
	public void onResume() {
		super.onResume();


		if(!isLoading){
			initLoading();
			isLoading=true;
		}
	}

	/**
	 * 恢复状态
	 *
	 * @author blue
	 */
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
	}
}
