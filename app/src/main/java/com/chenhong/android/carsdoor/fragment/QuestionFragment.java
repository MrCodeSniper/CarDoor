package com.chenhong.android.carsdoor.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.AnswerActivity;
import com.chenhong.android.carsdoor.activity.LoginActivity;
import com.chenhong.android.carsdoor.activity.MapActivity;
import com.chenhong.android.carsdoor.activity.SendQuestionActivity;
import com.chenhong.android.carsdoor.adapter.MyQuesRecyclerAdapter;
import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.adapter.QuestionsAdapter;
import com.chenhong.android.carsdoor.adapter.QuickQuesAdapter;
import com.chenhong.android.carsdoor.entity.Questions;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.CacheUtils;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.CircleRefreshLayout;
import com.chenhong.android.carsdoor.view.CustomPtrHeader;
import com.chenhong.android.carsdoor.view.RiseNumberView.NumberScrollTextView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.melnykov.fab.FloatingActionButton;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 问答
 * 
 * @author blue
 */
public class QuestionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener

{   //日志tag
	private String TAG = "QuestionFragment" ;
	@ViewInject(R.id.lv_ques)
	private RecyclerView lv_ques;

	@ViewInject(R.id.swipeLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
//	@ViewInject(R.id.load_more_list_view_container)
//	private LoadMoreListViewContainer loadMoreListViewContainer;
	private int count=10;
	private int curPage = 0;		// 当前页的编号，从0开始

	@ViewInject(R.id.layout_no)
	private LinearLayout layout_nodata;

//	private CustomPtrHeader header;
	private QuickQuesAdapter adapter;
	private  boolean isUpdate=false;

	private boolean fromNet=true;


	private NumberScrollTextView numberScrollTextView;

	private NumberScrollTextView numberScrollTextView2;
	public static final int STATE_REFRESH = 0;// 下拉刷新
	public static final int STATE_MORE = 1;// 加载更多
	private List<Questions> questionses = new ArrayList<Questions>();
    @ViewInject(R.id.fab)
	private FloatingActionButton fab;

//
//	private static final int PAGE_SIZE = 4;
//
//	private int delayMillis = 1000;
//	private boolean isErr;
//	private View notLoadingView;


	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_undoques;
	}

	@Override
	protected void initParams()
	{
//swipeRefreshLayout.setRefreshing(true);无效
		swipeRefreshLayout.post(new Runnable(){
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});

		fab.attachToRecyclerView(lv_ques);
		swipeRefreshLayout.setOnRefreshListener(this);
		lv_ques.setLayoutManager(new LinearLayoutManager(getActivity()));

	}


	private void addHeadView() {
		View headView = getActivity().getLayoutInflater().inflate(R.layout.head, (ViewGroup) lv_ques.getParent(), false);
		numberScrollTextView= (NumberScrollTextView) headView.findViewById(R.id.tv_leiji_num);
		numberScrollTextView2= (NumberScrollTextView) headView.findViewById(R.id.tv_jinri_num);

//		final View customLoading = getActivity().getLayoutInflater().inflate(R.layout.custom_loading, (ViewGroup) mRecyclerView.getParent(), false);
//		headView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mQuickAdapter.setLoadingView(customLoading);
//				mRecyclerView.setAdapter(mQuickAdapter);
//				Toast.makeText(PullToRefreshUseActivity.this, "use ok!", Toast.LENGTH_LONG).show();
//			}
//		});
		numberScrollTextView.setFromAndEndNumber(0, 118000);
		numberScrollTextView.setDuration(3000);
		numberScrollTextView.start();
		numberScrollTextView2.setFromAndEndNumber(0, 101);
		numberScrollTextView2.setDuration(3000);
		numberScrollTextView2.start();
		adapter.addHeaderView(headView);
	}







	private void queryQusetions(int page, final int actionType) {

		 final BmobQuery<Questions> query = new BmobQuery<>();
		query.order("-createdAt");
		String end = "2016-09-10 23:59:59";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1  = null;
		try {
			date1 = sdf1.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(actionType == STATE_MORE){
			// 只查询大于等于nid0的数据
			query.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date1));
			// 跳过之前页数并去掉重复数据
			query.setSkip(page * count+1);
		}else{
			page=0;
			//跳过第多少条数据，分页时用到，获取下一页数据
			query.setSkip(page);
		}
		// 设置每页数据个数
		query.setLimit(count);

		// 查找数据
		final int finalPage = page;

		//缓存
		boolean isCache = query.hasCachedResult(Questions.class);
		if(isCache&&fromNet==false){
			query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}
		Log.e("tazz",isCache+""+fromNet+":isCache");


        query.findObjects(new FindListener<Questions>() {
			@Override
			public void done(List<Questions> list, BmobException e) {
				if(e==null){
					if(list.size()>0){
						if(actionType == STATE_REFRESH){
							// 当是下拉刷新操作时，将当前页的编号重置为0，并把集合清空，重新添加
							curPage = 0;
							questionses.clear();
							lv_ques.setVisibility(View.VISIBLE);
							layout_nodata.setVisibility(View.GONE);
						}
						questionses.addAll(list);
						if(adapter!=null){
//							loadMoreListViewContainer.loadMoreFinish(false,true);
							adapter.notifyDataSetChanged();
							numberScrollTextView.setFromAndEndNumber(0, 118000);
							numberScrollTextView.setDuration(3000);
							numberScrollTextView.start();
							numberScrollTextView2.setFromAndEndNumber(0, 101);
							numberScrollTextView2.setDuration(3000);
							numberScrollTextView2.start();
						}else {
							adapter = new QuickQuesAdapter(questionses);
							adapter.openLoadAnimation();
							lv_ques.setAdapter(adapter);
							adapter.setOnLoadMoreListener(QuestionFragment.this);
							addHeadView();
						}
						adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
							@Override
							public void onItemClick(View view, int i) {
								Intent intent=new Intent(getActivity(), AnswerActivity.class);
								Bundle bundle=new Bundle();
								bundle.putSerializable("question",questionses.get(i));
                                intent.putExtras(bundle);
								startActivity(intent);
							}
						});
//						rvheader.attachTo(lv_ques,true);
						// 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
						curPage++;
						swipeRefreshLayout.setRefreshing(false);
						isUpdate=false;
						MyUtils.showToast(getActivity(),("第" + (finalPage + 1) + "页数据加载完成"));
					}else if(actionType == STATE_MORE){
						if(isUpdate){
//							loadMoreListViewContainer.loadMoreFinish(true,false);
							isUpdate=false;
						}
					}else if(actionType == STATE_REFRESH){
						if(isUpdate){
							swipeRefreshLayout.setRefreshing(false);
							isUpdate=false;
						}
						MyUtils.showToast(getActivity(), "没有数据");
					}
				}else {
					if(actionType == STATE_REFRESH){
						Log.e("tazzz",e.getMessage()+"");
						swipeRefreshLayout.setRefreshing(false);
						lv_ques.setVisibility(View.GONE);
						layout_nodata.setVisibility(View.VISIBLE);
					}else if(actionType == STATE_MORE) {
						if (isUpdate) {
//							loadMoreListViewContainer.loadMoreFinish(true,false);
							isUpdate = false;
							MyUtils.showToast(getActivity(), "没有更多数据了");
						}
					}
				}
			}
		});


	}

	@Override
	protected void initLoading() {
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				onRefresh();
			}
		}, 200);
		fromNet=false;
	}


  @OnClick(R.id.fab)
  public void onclick(View view){
     if(BmobUser.getCurrentUser(_User.class)!=null){
		 Intent intent=new Intent(getActivity(), SendQuestionActivity.class);
		 startActivityForResult(intent,Constant.QUESTION_SEND_REQUEST);
	 }else {
		 Intent intent=new Intent(getActivity(), LoginActivity.class);
		 startActivityForResult(intent, Constant.QUESTION_LOGIN_REQUEST);
	 }


  }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("tag","执行了onActivityResult");
		switch (requestCode){
			case Constant.QUESTION_LOGIN_REQUEST:
				if(BmobUser.getCurrentUser(_User.class)!=null){
					Intent intent=new Intent(getActivity(), SendQuestionActivity.class);
					startActivity(intent);
				}
				break;
           case Constant.QUESTION_SEND_REQUEST:

			   if(data!=null){
				   if(data.getBooleanExtra("isSendSuccess",false)){
					   if(swipeRefreshLayout!=null){
						   fromNet=true;
						   onRefresh();
					   }
				   }
			   }
			   break;
			default:
				break;


		}

	}


//	@Override
//	public void onListLoad() {
//		isUpdate=true;
//		queryQusetions(curPage, STATE_MORE);
//	}

	@Override
	public void onRefresh() {
		isUpdate = true;
		queryQusetions(0,STATE_REFRESH);
//		numberScrollTextView.setFromAndEndNumber(0, 118000);
//		numberScrollTextView.setDuration(3000);
//		numberScrollTextView.start();
//		numberScrollTextView2.setFromAndEndNumber(0, 101);
//		numberScrollTextView2.setDuration(3000);
//		numberScrollTextView2.start();
	}

	@Override
	public void onLoadMoreRequested() {
		isUpdate=true;
		queryQusetions(curPage, STATE_MORE);
	}
}
