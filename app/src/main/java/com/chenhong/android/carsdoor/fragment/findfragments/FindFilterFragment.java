package com.chenhong.android.carsdoor.fragment.findfragments;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.FilterAdapter;
import com.chenhong.android.carsdoor.adapter.FindFilterViewPagerAdapter;
import com.chenhong.android.carsdoor.entity.SearchCar;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.utils.DisplayUtil;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * 找车-品牌找车
 * 
 * @author blue
 */
public class FindFilterFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{
	// 已选条件栏
	@ViewInject(R.id.find_filter_hscv_choosed)
	HorizontalScrollView find_filter_hscv_choosed;
	@ViewInject(R.id.find_filter_llyt_choosed)
	LinearLayout find_filter_llyt_choosed;
	@ViewInject(R.id.fragment_find_filter_add_llyt)
	LinearLayout fragment_find_filter_add_llyt;

	@ViewInject(R.id.fragment_find_filter_vp)
	ViewPager find_filter_vp;
	@ViewInject(R.id.tabs)
	PagerSlidingTabStrip tabs;
	@ViewInject(R.id.ll_select)
	LinearLayout ll_select;
	@ViewInject(R.id.fragment_find_filter_btn_result)
	Button btn_result;



	// 价格选项
	private Button fragment_find_price_btn_price1;
	private Button fragment_find_price_btn_price2;
	private Button fragment_find_price_btn_price3;

	// 级别选项
	private Button fragment_find_level_btn_level1;
	private Button fragment_find_level_btn_level2;
	private Button fragment_find_level_btn_level3;

	// 排量选项
	private Button fragment_find_pai_btn_pai1;
	private Button fragment_find_pai_btn_pai2;
	private Button fragment_find_pai_btn_pai3;

	// 变速箱选项
	private Button fragment_find_bian_btn_bian1;
	private Button fragment_find_bian_btn_bian2;
	private Button fragment_find_bian_btn_bian3;
	String[] title = { "价格", "级别", "排量" ,"变速箱"};
	// viewpager数据源
	private List<View> viewList = new ArrayList<>();
	// 当前选中项
	private int currIndex = 0;
	// 图片居中位移
	private int offset;
	// 游标图片宽度
	private int bmpW;
	private Animation mShowAction;
	private Animation mHiddenAction;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_find_filter_main;
	}

	@Override
	protected void initParams()
	{


		initAnimation();

		tabs.setIndicatorColor(getResources().getColor(R.color.find_cl_unchoose));
		tabs.setIndicatorHeight(DisplayUtil.dip2px(getActivity(),2));
		tabs.setDividerColor(android.R.color.transparent);
		tabs.setShouldExpand(true);


		// 切换的四个界面初始化
		LinearLayout price_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_price_main, null);
		LinearLayout level_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_level_main, null);
		LinearLayout pai_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_pai_main, null);
		LinearLayout bian_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_bian_main, null);

		// 初始化价格布局
		initPriceLayout(price_layout);
		// 初始化级别布局
		initLevelLayout(level_layout);
		// 初始化排量布局
		initPaiLayout(pai_layout);
		// 初始化变速箱布局
		initBianLayout(bian_layout);

		viewList.add(price_layout);
		viewList.add(level_layout);
		viewList.add(pai_layout);
		viewList.add(bian_layout);

		find_filter_vp.setAdapter(new FindFilterViewPagerAdapter(viewList,title));
		find_filter_vp.setCurrentItem(currIndex);
		tabs.setViewPager(find_filter_vp);
		tabs.setOnPageChangeListener(this);
	}

	@Override
	protected void initLoading() {

	}

	private void initAnimation() {
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);

		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f);
		mHiddenAction.setDuration(100);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}


	// 初始化价格布局
	private void initPriceLayout(LinearLayout layout)
	{
		fragment_find_price_btn_price1 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price1);
		fragment_find_price_btn_price2 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price2);
		fragment_find_price_btn_price3 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price3);

		fragment_find_price_btn_price1.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

                 setllselect();
				if(!isPrice){
					isPrice=true;
					add("price",fragment_find_price_btn_price1.getText()+"");
				}else {
					update("price",fragment_find_price_btn_price1.getText()+"");
				}

				fragment_find_price_btn_price1.setEnabled(false);
				fragment_find_price_btn_price2.setEnabled(true);
				fragment_find_price_btn_price3.setEnabled(true);
			}
		});
		fragment_find_price_btn_price2.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isPrice){
					isPrice=true;
					add("price",fragment_find_price_btn_price2.getText()+"");
				}else {
					update("price",fragment_find_price_btn_price2.getText()+"");
				}
				fragment_find_price_btn_price1.setEnabled(true);
				fragment_find_price_btn_price2.setEnabled(false);
				fragment_find_price_btn_price3.setEnabled(true);
			}
		});
		fragment_find_price_btn_price3.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isPrice){
					isPrice=true;
					add("price",fragment_find_price_btn_price3.getText()+"");
				}else {
					update("price",fragment_find_price_btn_price3.getText()+"");
				}
				fragment_find_price_btn_price1.setEnabled(true);
				fragment_find_price_btn_price2.setEnabled(true);
				fragment_find_price_btn_price3.setEnabled(false);
			}
		});





	}

	// 初始化级别布局
	private void initLevelLayout(LinearLayout layout)
	{
		fragment_find_level_btn_level1 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level1);
		fragment_find_level_btn_level2 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level2);
		fragment_find_level_btn_level3 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level3);

		fragment_find_level_btn_level1.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isLevel){
					isLevel=true;
					add("level",fragment_find_level_btn_level1.getText()+"");
				}else {
					update("level",fragment_find_level_btn_level1.getText()+"");
				}

				fragment_find_level_btn_level1.setEnabled(false);
				fragment_find_level_btn_level2.setEnabled(true);
				fragment_find_level_btn_level3.setEnabled(true);
			}
		});
		fragment_find_level_btn_level2.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isLevel){
					isLevel=true;
					add("level",fragment_find_level_btn_level2.getText()+"");
				}else {
					update("level",fragment_find_level_btn_level2.getText()+"");
				}
				fragment_find_level_btn_level1.setEnabled(true);
				fragment_find_level_btn_level2.setEnabled(false);
				fragment_find_level_btn_level3.setEnabled(true);
			}
		});
		fragment_find_level_btn_level3.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isLevel){
					isLevel=true;
					add("level",fragment_find_level_btn_level3.getText()+"");
				}else {
					update("level",fragment_find_level_btn_level3.getText()+"");
				}
				fragment_find_level_btn_level1.setEnabled(true);
				fragment_find_level_btn_level2.setEnabled(true);
				fragment_find_level_btn_level3.setEnabled(false);
			}
		});
	}

	// 初始化排量布局
	private void initPaiLayout(LinearLayout layout)
	{

		fragment_find_pai_btn_pai1 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai1);
		fragment_find_pai_btn_pai2 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai2);
		fragment_find_pai_btn_pai3 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai3);

		fragment_find_pai_btn_pai1.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isOut){
					isOut=true;
					add("pai",fragment_find_pai_btn_pai1.getText()+"");
				}else {
					update("pai",fragment_find_pai_btn_pai1.getText()+"");
				}
				fragment_find_pai_btn_pai1.setEnabled(false);
				fragment_find_pai_btn_pai2.setEnabled(true);
				fragment_find_pai_btn_pai3.setEnabled(true);
			}
		});
		fragment_find_pai_btn_pai2.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isOut){
					isOut=true;
					add("pai",fragment_find_pai_btn_pai2.getText()+"");
				}else {
					update("pai",fragment_find_pai_btn_pai2.getText()+"");
				}
				fragment_find_pai_btn_pai1.setEnabled(true);
				fragment_find_pai_btn_pai2.setEnabled(false);
				fragment_find_pai_btn_pai3.setEnabled(true);
			}
		});
		fragment_find_pai_btn_pai3.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isOut){
					isOut=true;
					add("pai",fragment_find_pai_btn_pai3.getText()+"");
				}else {
					update("pai",fragment_find_pai_btn_pai3.getText()+"");
				}
				fragment_find_pai_btn_pai1.setEnabled(true);
				fragment_find_pai_btn_pai2.setEnabled(true);
				fragment_find_pai_btn_pai3.setEnabled(false);
			}
		});
	}

	// 初始化变速箱布局
	private void initBianLayout(LinearLayout layout)
	{
		fragment_find_bian_btn_bian1 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian1);
		fragment_find_bian_btn_bian2 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian2);
		fragment_find_bian_btn_bian3 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian3);

		fragment_find_bian_btn_bian1.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isChangeV){
					isChangeV=true;
					add("bian",fragment_find_bian_btn_bian1.getText()+"");
				}else {
					update("bian",fragment_find_bian_btn_bian1.getText()+"");
				}
				fragment_find_bian_btn_bian1.setEnabled(false);
				fragment_find_bian_btn_bian2.setEnabled(true);
				fragment_find_bian_btn_bian3.setEnabled(true);
			}
		});
		fragment_find_bian_btn_bian2.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isChangeV){
					isChangeV=true;
					add("bian",fragment_find_bian_btn_bian2.getText()+"");
				}else {
					update("bian",fragment_find_bian_btn_bian2.getText()+"");
				}
				fragment_find_bian_btn_bian1.setEnabled(true);
				fragment_find_bian_btn_bian2.setEnabled(false);
				fragment_find_bian_btn_bian3.setEnabled(true);
			}
		});
		fragment_find_bian_btn_bian3.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				setllselect();
				if(!isChangeV){
					isChangeV=true;
					add("bian",fragment_find_bian_btn_bian3.getText()+"");
				}else {
					update("bian",fragment_find_bian_btn_bian3.getText()+"");
				}
				fragment_find_bian_btn_bian1.setEnabled(true);
				fragment_find_bian_btn_bian2.setEnabled(true);
				fragment_find_bian_btn_bian3.setEnabled(false);
			}
		});
	}


	private boolean isPrice=false;//是否被选中过
	private boolean isLevel=false;
	private boolean isOut=false;
	private boolean isChangeV=false;


	private void add(final String tag, String text){
		btn_result.setText("快速筛选");
		btn_result.setClickable(true);
		btn_result.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				querydata();
			}
		});

		final LinearLayout choosedItem = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_add_main, null);
		final LinearLayout fragment_find_filter_add_llyt = (LinearLayout) choosedItem.findViewById(R.id.fragment_find_filter_add_llyt);
		TextView fragment_find_filter_add_txt = (TextView) choosedItem.findViewById(R.id.fragment_find_filter_add_txt);
        choosedItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

               if(tag.equals("price")){
				   isPrice=false;//设置没有加载
				   clearPrice();
			   }else if(tag.equals("level")){
				   isLevel=false;
				   clearLevel();
			   }else if(tag.equals("pai")) {
				   isOut=false;
				   clearPai();
			   }else if(tag.equals("bian")) {
				   isChangeV=false;
				   clearBian();
			   }
				find_filter_llyt_choosed.removeView(choosedItem);
				if(find_filter_llyt_choosed.getChildCount()==0){
					ll_select.startAnimation(mHiddenAction);
					ll_select.setVisibility(View.GONE);
					if(!btn_result.getText().toString().equals("返回筛选")){
						btn_result.setText("请选择筛选条件");
						btn_result.setClickable(false);
					}
				}

			}
		});
		fragment_find_filter_add_txt.setText(text);
		choosedItem.setTag(tag);
		find_filter_llyt_choosed.addView(choosedItem);//设置tag并加
		find_filter_hscv_choosed.post(new Runnable()
		{
			@Override
			public void run()
			{   //宽度根据子类的内容变化
//				find_filter_hscv_choosed.smoothScrollTo(find_filter_hscv_choosed.getLayoutParams().width, 0);
				find_filter_hscv_choosed.smoothScrollTo(find_filter_hscv_choosed.getWidth(), 0);

			}
		});

	}


	private void update(String tag,String text){
		for(int i=0;i<find_filter_llyt_choosed.getChildCount();i++){
			LinearLayout tmp = (LinearLayout) find_filter_llyt_choosed.getChildAt(i);
			String tmpTag = (String) tmp.getTag();
			if(tmpTag.equals(tag)){
				find_filter_llyt_choosed.removeView(tmp);
				add(tag,text);
				break;
			}
		}
	}




	// 清空价格条件
	private void clearPrice()
	{
		fragment_find_price_btn_price1.setEnabled(true);
		fragment_find_price_btn_price2.setEnabled(true);
		fragment_find_price_btn_price3.setEnabled(true);
	}

	// 清空级别条件
	private void clearLevel()
	{
		fragment_find_level_btn_level1.setEnabled(true);
		fragment_find_level_btn_level2.setEnabled(true);
		fragment_find_level_btn_level3.setEnabled(true);
	}

	// 清空排量条件
	private void clearPai()
	{
		fragment_find_pai_btn_pai1.setEnabled(true);
		fragment_find_pai_btn_pai2.setEnabled(true);
		fragment_find_pai_btn_pai3.setEnabled(true);
	}

	// 清空变速箱条件
	private void clearBian()
	{
		fragment_find_bian_btn_bian1.setEnabled(true);
		fragment_find_bian_btn_bian2.setEnabled(true);
		fragment_find_bian_btn_bian3.setEnabled(true);
	}



	private void setllselect(){
		if(ll_select.getVisibility()==View.GONE){
			ll_select.setVisibility(View.VISIBLE);
			ll_select.startAnimation(mShowAction);
		}
	}


	private List<String> tags=new ArrayList<>();



	private void querydata() {
		int count=find_filter_llyt_choosed.getChildCount();
		Log.e("tazzz",find_filter_llyt_choosed.getChildCount()+"");
		for(int i=0;i<count;i++){
			LinearLayout ll = (LinearLayout) find_filter_llyt_choosed.getChildAt(i);
			LinearLayout l= (LinearLayout) ll.getChildAt(0);
			TextView fragment_find_filter_add_txt = (TextView) l.findViewById(R.id.fragment_find_filter_add_txt);
			Log.e("tazzz", fragment_find_filter_add_txt.getText().toString()+"");
			tags.add(fragment_find_filter_add_txt.getText().toString());
		}

		switch (tags.size()){
			case 1:
				BmobQuery<SearchCar> bq = new BmobQuery<SearchCar>();
                query(bq,0);
				findquery(bq);
				break;
			case 2:
				List<BmobQuery<SearchCar>> queries = new ArrayList<BmobQuery<SearchCar>>();
				andquery(2,queries);
				BmobQuery<SearchCar> bq2=new BmobQuery<SearchCar>();
				bq2.and(queries);
				findquery(bq2);
				break;
			case 3:
				List<BmobQuery<SearchCar>> queries3 = new ArrayList<BmobQuery<SearchCar>>();
				andquery(3,queries3);
				BmobQuery<SearchCar> bq3=new BmobQuery<SearchCar>();
				bq3.and(queries3);
				findquery(bq3);
				break;
			case 4:
				List<BmobQuery<SearchCar>> queries4 = new ArrayList<BmobQuery<SearchCar>>();
				andquery(4,queries4);
				BmobQuery<SearchCar> bq4=new BmobQuery<SearchCar>();
				bq4.and(queries4);
				findquery(bq4);
				break;
		}
	}

	private FilterAdapter filterAdapter;


	private void query(BmobQuery<SearchCar> bq,int i){
		if(tags.get(i).contains("万")){
			bq.addWhereEqualTo("price_tag",tags.get(i));
		}else if(tags.get(i).contains("型")){
			bq.addWhereEqualTo("type",tags.get(i));
		}else if(tags.get(i).contains("L")){
			bq.addWhereEqualTo("out",tags.get(i));
		}else {
			bq.addWhereEqualTo("gearbox",tags.get(i));
		}
	}

	private void andquery(int size,List<BmobQuery<SearchCar>> queries){
		for(int i=0;i<size;i++){
			BmobQuery<SearchCar> bq = new BmobQuery<SearchCar>();
			query(bq,i);
			queries.add(bq);
		}
	}

	@ViewInject(R.id.lv_filter)
	private ListView lv_filter;
	@ViewInject(R.id.ll_filter)
	private LinearLayout ll_filter;

	private void  findquery(BmobQuery<SearchCar> bq){
		bq.findObjects(new FindListener<SearchCar>() {
			@Override
			public void done(List<SearchCar> list, BmobException e) {
				if(e==null){
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							Log.e("tazzz",list.get(i).getName());
							lv_filter.setVisibility(View.VISIBLE);
							ll_filter.setVisibility(View.GONE);
							if(filterAdapter==null){
								lv_filter.setAdapter(new FilterAdapter(getActivity(),list));
							}else {
								filterAdapter.refreshDatas(list);
								filterAdapter.notifyDataSetChanged();
							}
							btn_result.setText("返回筛选");
							btn_result.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									lv_filter.setVisibility(View.GONE);
									ll_filter.setVisibility(View.VISIBLE);
									if(find_filter_llyt_choosed.getChildCount()==0){
										btn_result.setText("请选择筛选条件");
									}else {
										btn_result.setText("快速筛选");
									}
									btn_result.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											querydata();
										}
									});
								}
							});
						}
					}else {
						MyUtils.showToast(getActivity(),"未找到对应的车型");
					}
				}else{
					Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
				}
				tags.clear();
			}
		});
	}



private boolean isAdded=false;
	WindowManager.LayoutParams params;
	WindowManager wm;
	Button btn_floatView;
	private void createFloatView()
{

	btn_floatView = new Button(getActivity());
	btn_floatView.setText("悬浮窗");

	wm = (WindowManager) getActivity().getApplicationContext().getSystemService(
			Context.WINDOW_SERVICE);



	params = new WindowManager.LayoutParams();

	// 设置window type
	params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        /*
         * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
         * 即拉下通知栏不可见
         */

	params.format = PixelFormat.TRANSLUCENT; // 设置图片格式，效果为背景透明

	// 设置Window flag
	params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

	params.gravity = Gravity.RIGHT| Gravity. CENTER_VERTICAL;

//	params.x=0;
//	params.y=0;
        /*
         * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
         * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
         */

	// 设置悬浮窗的长得宽
	params.width = 100;
	params.height =100;

//	// 设置悬浮窗的Touch监听
//	btn_floatView.setOnTouchListener(new View.OnTouchListener()
//	{
//		int lastX, lastY;
//		int paramX, paramY;
//
//		public boolean onTouch(View v, MotionEvent event)
//		{
//			switch (event.getAction())
//			{
//				case MotionEvent.ACTION_DOWN:
//					lastX = (int) event.getRawX();
//					lastY = (int) event.getRawY();
//					paramX = params.x;
//					paramY = params.y;
//					break;
//				case MotionEvent.ACTION_MOVE:
//					int dx = (int) event.getRawX() - lastX;
//					int dy = (int) event.getRawY() - lastY;
//					params.x = paramX + dx;
//					params.y = paramY + dy;
//					// 更新悬浮窗位置
//					wm.updateViewLayout(btn_floatView, params);
//					break;
//			}
//			return true;
//		}
//	});
	if(btn_floatView.getParent()==null){
		Log.e("tazzz","add");
		wm.addView(btn_floatView, params);
		isAdded = true;
	}

}




}
