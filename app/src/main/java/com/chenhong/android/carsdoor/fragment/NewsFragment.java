package com.chenhong.android.carsdoor.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.MainActivity;
import com.chenhong.android.carsdoor.activity.SearchActivity;
import com.chenhong.android.carsdoor.factory.FragmentFactory;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.DisplayUtil;
import com.chenhong.android.carsdoor.view.PopWinMenu;
import com.chenhong.android.carsdoor.view.RotateAnimation;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃   神兽保佑
 * ┃　　　┃   代码无BUG！
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 */
public class NewsFragment extends BaseFragment {
    //顶部导航的高度
    public static int StruesHeight;
    // Ҫ��
    @ViewInject(R.id.news_tv_important)
    TextView news_tv_important;
    // �³�
    @ViewInject(R.id.news_tv_car)
    TextView news_tv_car;
    // ����
    @ViewInject(R.id.news_tv_buy)
    TextView news_tv_buy;
    // �Գ�
    @ViewInject(R.id.news_tv_try)
    TextView news_tv_try;
    // ͼ��
    @ViewInject(R.id.news_tv_picture)
    TextView news_tv_picture;
    // ��ҵ
    @ViewInject(R.id.news_tv_market)
    TextView news_tv_market;
    // viewpager
    @ViewInject(R.id.news_vp)
    ViewPager news_vp;

    @ViewInject(R.id.ib_search)
    private ImageButton ib_search;

    @ViewInject(R.id.ll_extra)
    public LinearLayout ll_extra;
    @ViewInject(R.id.rl_search)
    public RelativeLayout rl_search;

    @ViewInject(R.id.iv_news_search)
    private ImageView iv_news_search;

    @ViewInject(R.id.iv_menu)
    public ImageView iv_menu;

    @ViewInject(R.id.iv_help)
    private ImageView iv_help;
    @ViewInject(R.id.iv_msg)
    private ImageView iv_msg;

    private PopWinMenu popWinShare;

    @ViewInject(R.id.rl_title)
    private RelativeLayout rl_title;
    public boolean isEnd = true;


    private List<Fragment> fragmentlist = new ArrayList<Fragment>();

    private int[] fragments = {Constant.NEWSIMP, Constant.NEWSCAR,
            Constant.NEWSBUY, Constant.NEWSTRY, Constant.NEWSPIC,
            Constant.NEWSMAR};
    private Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initParams() {

        news_vp.setCurrentItem(0);
        android.util.Log.d("mark", "onCreateView()--------->news Fragment");
        for (int i = 0; i < fragments.length; i++) {
            fragmentlist.add(FragmentFactory.newInstance(fragments[i]));
        }

        news_vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return fragmentlist.get(arg0);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragmentlist.size();
            }

        });

        // Ĭ��ѡ�е�һҳ��
        // ��ҳ���л�������
        news_vp.setOnPageChangeListener(new DefineOnPageChangeListener());


        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWinShare == null) {
                    //自定义的单击事件
                    OnClickLintener paramOnClickListener = new OnClickLintener();
                    popWinShare = new PopWinMenu(getActivity(), paramOnClickListener, DisplayUtil.dip2px(getActivity(), 160), DisplayUtil.dip2px(getActivity(), 120));
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                }
//设置默认获取焦点
                popWinShare.setFocusable(true);
//以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(iv_menu, 0, 0);
//如果窗口存在，则更新
                popWinShare.update();
            }
        });


        ViewTreeObserver vto = rl_title.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // TODO Auto-generated method stub
                StruesHeight = rl_title.getMeasuredHeight();
                return true;
            }
        });
    }

    @Override
    protected void initLoading() {

    }

    public void showSearch() {
//		applyRotation(-1, 180, 90);
        if (rl_search.getVisibility() == View.VISIBLE && ll_extra.getVisibility() == View.GONE) {
            Log.e("tazzz", "show不操作");
        } else {
            applyRotation(0, 0, 90);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//默认从（0,0）
            //(0.5f,1,0.5f,1)放大(1,0.5f,1,0.5f)缩小
            scaleAnimation.setDuration(300);
            rl_search.startAnimation(scaleAnimation);
//			rl_search.setVisibility(View.VISIBLE);
//			ll_extra.setVisibility(View.GONE);
        }

    }

    public void hideSearch() {
        if (rl_search.getVisibility() == View.GONE && ll_extra.getVisibility() == View.VISIBLE) {
            Log.e("tazzz", "hide不操作");
        } else {
            applyRotation(0, 90, 0);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//默认从（0,0）
            //(0.5f,1,0.5f,1)放大(1,0.5f,1,0.5f)缩小
            scaleAnimation.setDuration(200);
            rl_search.startAnimation(scaleAnimation);
//			rl_search.setVisibility(View.GONE);
//			ll_extra.setVisibility(View.VISIBLE);
        }

    }


    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.help:
                    ((MainActivity) getActivity()).mTabHost.setCurrentTab(3);
                    popWinShare.dismiss();
                    break;
                case R.id.msg:
                    ((MainActivity) getActivity()).mTabHost.setCurrentTab(4);
                    popWinShare.dismiss();
                    break;

                default:
                    break;
            }

        }

    }


    private void applyRotation(int position, float start, float end) {
// 计算中心点
        final float centerX = ll_extra.getWidth() / 2.0f;
        final float centerY = ll_extra.getHeight() / 2.0f;
        final RotateAnimation rotation =
                new RotateAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(200);
//		rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());

        //设置监听
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isEnd = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                isEnd = true;

                if (rl_search.getVisibility() == View.VISIBLE) {
                    rl_search.setVisibility(View.GONE);
                } else {
                    rl_search.setVisibility(View.VISIBLE);
                }

                if (ll_extra.getVisibility() == View.VISIBLE) {
                    ll_extra.setVisibility(View.GONE);
                } else {
                    ll_extra.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_extra.startAnimation(rotation);


    }


    @OnClick({R.id.news_tv_buy, R.id.news_tv_car, R.id.news_tv_important,
            R.id.news_tv_market, R.id.news_tv_picture, R.id.news_tv_try, R.id.iv_news_search, R.id.ib_search, R.id.iv_help, R.id.iv_msg})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.news_tv_important:
                news_vp.setCurrentItem(0);
                break;
            case R.id.news_tv_car:
                news_vp.setCurrentItem(1);
                break;
            case R.id.news_tv_buy:
                news_vp.setCurrentItem(2);
                break;
            case R.id.news_tv_try:
                news_vp.setCurrentItem(3);
                break;
            case R.id.news_tv_picture:
                news_vp.setCurrentItem(4);
                break;
            case R.id.news_tv_market:
                news_vp.setCurrentItem(5);
                break;
            case R.id.iv_news_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_help:
                ((MainActivity) getActivity()).mTabHost.setCurrentTab(3);
                break;
            case R.id.iv_msg:
                ((MainActivity) getActivity()).mTabHost.setCurrentTab(4);
                break;
            default:

                break;
        }

    }

    // viewpager��ͼ�л�������
    public class DefineOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                // Ҫ��
                case 0:

                    news_tv_important.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
                // �³�
                case 1:

                    news_tv_car.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
                // ����
                case 2:

                    news_tv_buy.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
                // �Գ�
                case 3:

                    news_tv_try.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
                // ͼ��
                case 4:

                    news_tv_picture.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_market.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
                // ����
                case 5:

                    news_tv_market.setBackgroundResource(R.drawable.news_ic_selected);
                    news_tv_important.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_car.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_buy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_try.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    news_tv_picture.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    news_tv_market.setTextColor(getResources().getColor(R.color.news_cl_choose));
                    news_tv_important.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_car.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_buy.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_try.setTextColor(getResources().getColor(R.color.news_cl_unchoose));
                    news_tv_picture.setTextColor(getResources().getColor(R.color.news_cl_unchoose));

                    break;
            }
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.d("mark", "onCreate()--------->news Fragment");
    }


    @Override
    public void onPause() {
        super.onPause();
        android.util.Log.d("mark", "onPause()--------->news Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.d("mark", "onCreateDestroy--------->news Fragment");
    }
}
