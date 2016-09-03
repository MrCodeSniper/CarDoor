package com.chenhong.android.carsdoor.fragment.newsfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.MainActivity;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.fragment.NewsFragment;

import com.chenhong.android.carsdoor.view.parallax.OnTouchEventListener;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯-行业
 * 
 * @author blue
 */
public class NewsMarketFragment extends BaseFragment
{



	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_market_main;
	}

	@Override
	protected void initParams()
	{




	}

	@Override
	protected void initLoading() {

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint()) {

		}
		super.onActivityCreated(savedInstanceState);
	}


}
