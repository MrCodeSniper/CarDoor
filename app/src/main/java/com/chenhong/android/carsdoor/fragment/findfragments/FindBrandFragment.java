package com.chenhong.android.carsdoor.fragment.findfragments;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.GestureDetector;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.DetailCarActivity;
import com.chenhong.android.carsdoor.activity.MainActivity;
import com.chenhong.android.carsdoor.adapter.CarLogoAdapter;
import com.chenhong.android.carsdoor.adapter.CarSeriesAdapter;
import com.chenhong.android.carsdoor.entity.car_logo;
import com.chenhong.android.carsdoor.entity.car_series;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.utils.DisplayUtil;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.QuickIndexBar;
import com.chenhong.android.carsdoor.view.photoview.OnGestureListener;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 找车-品牌找车
 * 
 * @author blue
 */
public class FindBrandFragment extends BaseFragment implements QuickIndexBar.onTouchLetterListener,MainActivity.FragmentOnTouchListener
{
    @ViewInject(R.id.ptr)
	private PtrFrameLayout ptr;
	@ViewInject(R.id.qib)
	private QuickIndexBar quickIndexBar;
	@ViewInject(R.id.lv_content)
	private ListView lv_content;
	@ViewInject(R.id.currentindex)
	private TextView currentindex;
	@ViewInject(R.id.rl_total)
    private RelativeLayout real_content;

	// 车系找车
	@ViewInject(R.id.find_brand_llyt_content)
	LinearLayout find_brand_llyt_content;
	@ViewInject(R.id.find_cover_lv)
	ListView find_cover_lv;

	private Handler handler=new Handler(){
	};
	private CarLogoAdapter adapter;
	private CarSeriesAdapter adapter2;
	private List<car_logo> logos = new ArrayList<car_logo>();
	private List<car_series> series = new ArrayList<car_series>();
	private GestureDetector gestureDetector;

	private boolean isopen=false;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_find_brand_main;
	}


	@Override
	protected void initParams()
	{
		((MainActivity) getActivity()).registerfragmentOnTouchListener(this);
		initptr();
		quickIndexBar.setonTouchListener(this);
	}

	@Override
	protected void initLoading() {




	}








	private void queryfirstdata() {
		BmobQuery<car_logo> query = new BmobQuery<car_logo>();
		query.order("cid");
		query.addWhereGreaterThanOrEqualTo("cid", 1);
		query.setLimit(60);
         //执行查询方法
		query.findObjects(new FindListener<car_logo>() {
			@Override
			public void done(List<car_logo> list, final BmobException e) {
				if (e == null) {
					logos.clear();
					logos.addAll(list);
//					Collections.sort(logos);
					if(adapter==null){
						adapter=new CarLogoAdapter(getActivity().getApplicationContext(),logos);
						lv_content.setAdapter(adapter);
						lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
								if(!isShow){
									querySecondData(logos.get(i).getName());
									showContent();
								}else{
									querySecondData(logos.get(i).getName());
								}
							}
						});
					}else {
						adapter.refreshDatas(logos);
						adapter.notifyDataSetChanged();
					}
					quickIndexBar.setVisibility(View.VISIBLE);
					ptr.refreshComplete();
					isUpdate=false;
				} else {
					if(isUpdate){
						ptr.refreshComplete();
						isUpdate=false;
					}
					Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
				}
			}
		});
	}


	private void querySecondData(String name){

		BmobQuery<car_series> query = new BmobQuery<car_series>();
		query.order("cid");
		query.addWhereContains("alias_name", name);
		query.setLimit(60);
		//执行查询方法
		query.findObjects(new FindListener<car_series>() {
			@Override
			public void done(final List<car_series> list, BmobException e) {
				if (e == null) {
					Log.e("tazz","加载成功"+list.get(0).getName());
					series.clear();
					series.addAll(list);
//					Collections.sort(logos);
					if(adapter2==null){
						adapter2=new CarSeriesAdapter(getActivity().getApplicationContext(),series);
						find_cover_lv.setAdapter(adapter2);
					}else {
						adapter2.refreshDatas(series);
						adapter2.notifyDataSetChanged();
					}
					find_cover_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Intent intent=new Intent(getActivity(), DetailCarActivity.class);
							intent.putExtra("car",list.get(position).getAlias_name());
							startActivity(intent);
						}
					});
//					ptr.refreshComplete();
				} else {
//					if(isUpdate){
//						ptr.refreshComplete();
//					}
					MyUtils.showToast(getActivity(),"很抱歉，暂时没有数据");
					Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
				}
			}
		});



	}


//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		((MainActivity) getActivity()).UnregisterfragmentOnTouchListener();
//	}

	private boolean isUpdate=false;

	private void initptr(){

		gestureDetector = new GestureDetector(getActivity(), onGestureListener);
        //初始化storeheader样式
		StoreHouseHeader  header=new StoreHouseHeader(getActivity());
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1,-2));
		header.setPadding(0, DisplayUtil.dip2px(getActivity(),15),0,DisplayUtil.dip2px(getActivity(),10));
		header.initWithString("CAR CAR");
		header.setTextColor(getResources().getColor(android.R.color.black));
        ptr.setHeaderView(header);
		ptr.addPtrUIHandler(header); //header本身实现了UI效果的接口

		ptr.setPtrHandler(new PtrHandler() {//需要自己实现数据
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,lv_content,header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
              isUpdate=true;
				queryfirstdata();
			}
		});

     /* 延时100秒 */
		ptr.postDelayed(new Runnable() {
			@Override
			public void run() {
				ptr.autoRefresh();
			}
		}, 200);

//      /* 下拉时阻止事件分发 */
//		ptr.setInterceptEventWhileWorking(true);
	}



	@Override
	public void onTouchLetter(String letter) {
		//根据触摸的字母去集合中找到那个item然后放到顶端
		for (int i = 0; i < logos.size(); i++) {
			String firstletter = logos.get(i).getNameSpell().charAt(0) + "";
			if (letter.equals(firstletter)) {
				lv_content.setSelection(i);
				break;//只需要找到第一个
			}
		}
		//显示当前触摸字母
		currentindex.setText(letter);
		currentindex.setVisibility(View.VISIBLE);
		//设置属性动画
//                com.nineoldandroids.view.ViewPropertyAnimator.animate(currentindex).scaleX(1f).setInterpolator(new OvershootInterpolator()).setDuration(350).start();
//                com.nineoldandroids.view.ViewPropertyAnimator.animate(currentindex).scaleY(1f).setInterpolator(new OvershootInterpolator()).setDuration(350).start();
		//延时隐藏 handler在哪声明就在哪个线程中运行
		//每次post之前消除之前的视图
		handler.removeCallbacksAndMessages(null);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				currentindex.setVisibility(View.INVISIBLE);
			}
		}, 1000);
	}


     private boolean isShow=false;
	// 显示二级菜单
	public void showContent()
	{
		quickIndexBar.setVisibility(View.GONE);
		find_brand_llyt_content.setVisibility(View.VISIBLE);
		find_brand_llyt_content.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_from_right));
		isShow = true;
	}

	// 隐藏二级菜单
	public void closeContent()
	{
		quickIndexBar.setVisibility(View.VISIBLE);
		find_brand_llyt_content.setVisibility(View.GONE);
		find_brand_llyt_content.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.out_to_right));
		isShow = false;
	}

	@Override
	public boolean onTouch(MotionEvent event) {
      return  gestureDetector.onTouchEvent(event);//传给手势监听处理
	}


	// 手势滑动监听器
	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener()
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			// 手势滑动时失去焦点
			find_cover_lv.setPressed(false);
			find_cover_lv.setFocusable(false);
			find_cover_lv.setFocusableInTouchMode(false);

			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			float x = e2.getX() - e1.getX();
			// 向右滑动到一定距离时隐藏内容
			if (x > 100&&isShow==true)
			{
				closeContent();
			}
			return true;
		}
	};

	@Override
	public void onStart() {
		super.onStart();
		((MainActivity) getActivity()).registerfragmentOnTouchListener(this);
		Log.e("tazz","onstart");
}







	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden==true){
			((MainActivity) getActivity()).UnregisterfragmentOnTouchListener();
		}else {
			((MainActivity) getActivity()).registerfragmentOnTouchListener(this);
		}
	}


	@Override
	public void onPause() {
		super.onPause();
		((MainActivity) getActivity()).UnregisterfragmentOnTouchListener();
	}
}
