package com.chenhong.android.carsdoor.view.cycleview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.view.cycleview.CycleViewPager;

/**
 * 广告图片自动轮播控件</br>
 * 
 * <pre>
 *   集合ViewPager和指示器的一个轮播控件，主要用于一般常见的广告图片轮播，具有自动轮播和手动轮播功能 
 *   使用：只需在xml文件中使用{@code <com.minking.imagecycleview.ImageCycleView/>} ，
 *   然后在页面中调用  {@link #setImageResources(ArrayList, ImageCycleViewListener) }即可!
 *   
 *   另外提供{@link #startImageCycle() } \ {@link #pushImageCycle() }两种方法，用于在Activity不可见之时节省资源；
 *   因为自动轮播需要进行控制，有利于内存管理
 * </pre>
 * 
 */
public class ImageCycleView extends LinearLayout {

	/**
	 * 上下文
	 */
	private Context mContext;

	/**
	 * 图片轮播视图
	 */
	private CycleViewPager mBannerPager = null;

	/**
	 * 滚动图片视图适配器
	 */
	private ImageCycleAdapter mAdvAdapter;

	/**
	 * 图片轮播指示器控件
	 */
	private ViewGroup mGroup;

	/**
	 * 图片轮播指示器-个图
	 */
	private ImageView mImageView = null;

	/**
	 * 滚动图片指示器-视图列表
	 */
	private ImageView[] mImageViews = null;

	/**
	 * 图片滚动当前图片下标
	 */
	private int mImageIndex = 1;

	/**
	 * 手机密度
	 */
	private float mScale;

	/**
	 * @param context
	 */
	public ImageCycleView(Context context) {
		super(context);
		mContext = context;
		mScale = context.getResources().getDisplayMetrics().density;
		LayoutInflater.from(context).inflate(R.layout.my_scrollview, this);
		mBannerPager = (CycleViewPager) findViewById(R.id.news_vp);
		mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
		tv_title = (TextView) findViewById(R.id.news_tv_title);
		dotViewsList.add(findViewById(R.id.news_title_tv1));
		dotViewsList.add(findViewById(R.id.news_title_tv2));
		dotViewsList.add(findViewById(R.id.news_title_tv3));
		dotViewsList.add(findViewById(R.id.news_title_tv4));
		dotViewsList.add(findViewById(R.id.news_title_tv5));

		mBannerPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						// 开始图片滚动
						startImageTimerTask();
						break;
					default:
						// 停止图片滚动
						stopImageTimerTask();
						break;
				}
				return false;
			}
		});
	}

	/**
	 * @param context
	 * @param attrs
	 */




	private List<NewsTitle> infoList=new ArrayList<>();
	private TextView tv_title;
	private List<View> dotViewsList=new ArrayList<View>();

	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		mContext = context;
//		mScale = context.getResources().getDisplayMetrics().density;
//		LayoutInflater.from(context).inflate(R.layout.my_scrollview, this);
//		mBannerPager = (CycleViewPager) findViewById(R.id.news_vp);
//		mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
//		tv_title = (TextView) findViewById(R.id.news_tv_title);
//		dotViewsList.add(findViewById(R.id.news_title_tv1));
//		dotViewsList.add(findViewById(R.id.news_title_tv2));
//		dotViewsList.add(findViewById(R.id.news_title_tv3));
//		dotViewsList.add(findViewById(R.id.news_title_tv4));
//		dotViewsList.add(findViewById(R.id.news_title_tv5));
//
//		mBannerPager.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//					case MotionEvent.ACTION_UP:
//						// 开始图片滚动
//						startImageTimerTask();
//						break;
//					default:
//						// 停止图片滚动
//						stopImageTimerTask();
//						break;
//				}
//				return false;
//			}
//		});
//		// 滚动图片右下指示器视图
//		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
	}

	/**
	 * 装填图片数据
	 * 
	 * @param
	 * @param imageCycleViewListener
	 */
	public void setImageResources(ArrayList<NewsTitle> infoList, ImageCycleViewListener imageCycleViewListener) {
		// 清除所有子视图
//		mGroup.removeAllViews();
		// 图片广告数量
		this.infoList=infoList;
		final int imageCount = infoList.size();
		mImageViews = new ImageView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			int imageParams = (int) (mScale * 20 + 0.5f);// XP与DP转换，适应不同分辨率
			int imagePadding = (int) (mScale * 5 + 0.5f);
			LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			layout.setMargins(3, 0, 3, 0);
			mImageView.setLayoutParams(layout);
			//mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
			mImageViews[i] = mImageView;
//			if (i == 0) {
//				mImageViews[i].setBackgroundResource(R.drawable.icon_point_pre);
//			} else {
//				mImageViews[i].setBackgroundResource(R.drawable.icon_point);
//			}
//			mGroup.addView(mImageViews[i]);
		}
		mAdvAdapter = new ImageCycleAdapter(mContext, infoList, imageCycleViewListener);
		mBannerPager.setAdapter(mAdvAdapter);
		startImageTimerTask();
	}

	/**
	 * 开始轮播(手动控制自动轮播与否，便于资源控制)
	 */
	public void startImageCycle() {
		startImageTimerTask();
	}

	/**
	 * 暂停轮播——用于节省资源
	 */
	public void pushImageCycle() {
		stopImageTimerTask();
	}

	/**
	 * 开始图片滚动任务
	 */
	private void startImageTimerTask() {
		stopImageTimerTask();
		// 图片每3秒滚动一次
		mHandler.postDelayed(mImageTimerTask, 3000);
	}

	/**
	 * 停止图片滚动任务
	 */
	private void stopImageTimerTask() {
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();

	/**
	 * 图片自动轮播Task
	 */
	private Runnable mImageTimerTask = new Runnable() {

		@Override
		public void run() {
			if (mImageViews != null) {
				// 下标等于图片列表长度说明已滚动到最后一张图片,重置下标
				if ((++mImageIndex) == mImageViews.length + 1) {
					mImageIndex = 1;
				}
				mBannerPager.setCurrentItem(mImageIndex);
			}
		}
	};

	/**
	 * 轮播图片状态监听器
	 * 
	 * @author minking
	 */
	 class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask(); // 开始下次计时
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			
			if (index == 0 || index == mImageViews.length + 1) {
				return;
			}
			// 设置图片滚动指示器背景
			mImageIndex = index;
			index -= 1;
			dotViewsList.get(index).setBackgroundColor(getResources().getColor(R.color.news_title_choose));
			tv_title.setText(infoList.get(index).getUrl());
			for(int i=0;i<dotViewsList.size();i++){
				if(index!=i){
					dotViewsList.get(i).setBackgroundColor(getResources().getColor(R.color.news_title_unchoose));
				}
			}
		}

	}

	private class ImageCycleAdapter extends PagerAdapter {

		/**
		 * 图片视图缓存列表
		 */
		private ArrayList<ImageView> mImageViewCacheList;

		/**
		 * 图片资源列表
		 */
		private ArrayList<NewsTitle> mAdList = new ArrayList<NewsTitle>();

		/**
		 * 广告图片点击监听器
		 */
		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, ArrayList<NewsTitle> adList, ImageCycleViewListener imageCycleViewListener) {
			mContext = context;
			mAdList = adList;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<>();
		}

		@Override
		public int getCount() {
			return mAdList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			String imageUrl = mAdList.get(position).getImage().getUrl();
			ImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			} else {
				imageView = mImageViewCacheList.remove(0);
			}
			// 设置图片点击监听
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mImageCycleViewListener.onImageClick(mAdList.get(position),position, v);
				}
			});
			imageView.setTag(imageUrl);
			container.addView(imageView);
			mImageCycleViewListener.displayImage(imageUrl, imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			container.removeView(view);
			mImageViewCacheList.add(view);
		}

	}

	/**
	 * 轮播控件的监听事件
	 * 
	 * @author minking
	 */
	public static interface ImageCycleViewListener {

		/**
		 * 加载图片资源
		 * 
		 * @param imageURL
		 * @param imageView
		 */
		public void displayImage(String imageURL, ImageView imageView);

		/**
		 * 单击图片事件
		 *
		 * @param imageView
		 */
		public void onImageClick(NewsTitle info, int postion, View imageView);
	}

}
