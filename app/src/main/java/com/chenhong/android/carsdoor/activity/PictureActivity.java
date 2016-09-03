package com.chenhong.android.carsdoor.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.BasePagerAdapter;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.utils.DisplayUtil;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Android on 2016/7/26.
 */
public class PictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @ViewInject(R.id.car_picture_iv_back)
    ImageView car_picture_iv_back;
    @ViewInject(R.id.car_picture_tv_index)
    TextView car_picture_tv_index;
    @ViewInject(R.id.car_picture_vp)
    ViewPager car_picture_vp;
    private String[] bmobFileurls;
    private String[] objects;
    private int currentposition;
    @ViewInject(R.id.car_picture_iv_share)
    ImageView car_picture_iv_share;
    @ViewInject(R.id.car_picture_iv_fav)
    ImageView car_picture_iv_fav;

    private View choosedItem;
    private Dialog shareDialog;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;


    private Handler shareHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 授权取消
                case MSG_AUTH_CANCEL:
                    Toast.makeText(PictureActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
                    break;
                // 授权成功
                case MSG_AUTH_COMPLETE:
                    Toast.makeText(PictureActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                // 授权失败
                case MSG_AUTH_ERROR:
                    Toast.makeText(PictureActivity.this, "分享失败，请先安装第三方客户端", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void initView() {
        final TextView textView = new TextView(PictureActivity.this);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("datas");
        currentposition = bundle.getInt("currentposition");
        objects = bundle.getStringArray("objects");
        bmobFileurls = bundle.getStringArray("bmobFileurl");
        String[] bmobFileStrings = bundle.getStringArray("bmobFileString");
        car_picture_tv_index.setText((currentposition + 1) + "/" + bmobFileurls.length);
        String object = objects[currentposition];
        BmobQuery<_User> query = new BmobQuery<_User>();
        NewsTitle newsTitle = new NewsTitle();
        newsTitle.setObjectId(object);
        query.addWhereRelatedTo("likes", new BmobPointer(newsTitle));
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> object, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        if (BmobUser.getCurrentUser(_User.class).getObjectId().equals(object.get(i).getObjectId())) {
                            Log.e("taqqq", "有关系");
                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nor);
                            isFav = true;
                        } else {
                            Log.e("taqqq", "没关系");
                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                            isFav = false;
                        }
                    }
                    Log.i("bmob", "查询个数：" + object.size());
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }

        });


        car_picture_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        car_picture_iv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                start_location[0] += DisplayUtil.sp2px(PictureActivity.this, 10);
                start_location[1] += DisplayUtil.sp2px(PictureActivity.this, 5);

                BmobQuery<NewsTitle> query = new BmobQuery<NewsTitle>();
                query.getObject(objects[currentposition], new QueryListener<NewsTitle>() {

                    @Override
                    public void done(NewsTitle newsTitle, BmobException e) {
                        if (e == null) {
                            //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                            BmobRelation relation = new BmobRelation();
                            _User user = null;
                            if (BmobUser.getCurrentUser(_User.class) != null) {
                                user = BmobUser.getCurrentUser(_User.class);
                            } else {
                                MyUtils.showToast(PictureActivity.this, "请登录");
                                return;
                            }
                            if (!isFav) {
                                textView.setText("+1");
                                relation.add(user);
                                newsTitle.setLikes(relation);
                                newsTitle.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nor);
                                            Log.i("bmob", "多对多关联添加成功");
                                            isFav = true;
                                        } else {
                                            Log.i("bmob", "失败：" + e.getMessage());
                                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                                            isFav = false;
                                        }
                                    }
                                });
                            } else {
                                textView.setText("-1");
                                relation.remove(user);
                                newsTitle.setLikes(relation);
                                newsTitle.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Log.i("bmob", "关联关系删除成功");
                                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                                            isFav = false;
                                        } else {
                                            car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nor);
                                            isFav = true;
                                            Log.i("bmob", "失败：" + e.getMessage());
                                        }
                                    }

                                });
                            }
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });


                if (textView != null) {
                    ViewGroup parent = (ViewGroup) textView.getParent();
                    if (parent != null) {
                        parent.removeView(textView);
                    }
                }
                setAnim(textView, start_location);// 开始执行动画
            }
        });


        car_picture_iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (shareDialog == null) {
//                    choosedItem = getLayoutInflater().inflate(R.layout.share_dialog_main, null);
//                    // 新浪微博
//                    LinearLayout share_llyt_sina = (LinearLayout) choosedItem.findViewById(R.id.share_llyt_sina);
//                    LinearLayout share_llyt_wx = (LinearLayout) choosedItem.findViewById(R.id.share_llyt_wechat);
//                    share_llyt_sina.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Log.e("tazz", bmobFileurls[currentposition]);
//                            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//                            sp.setText("我分享了一张图片，大家快来看！");
//                            sp.setImageUrl(bmobFileurls[currentposition]);
//                            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                            // 设置分享事件回调
//                            weibo.setPlatformActionListener(new ShareListener());
//                            // 执行图文分享
//                            weibo.share(sp);
//                            shareDialog.dismiss();
//                            Toast.makeText(PictureActivity.this, "正在分享，请稍候", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    share_llyt_wx.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Wechat.ShareParams sp = new Wechat.ShareParams();
//                            sp.setText("我分享了一张图片，大家快来看！");
//                            sp.setImageUrl(bmobFileurls[currentposition]);
//                            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//                            // 设置分享事件回调
//                            wechat.setPlatformActionListener(new ShareListener());
//                            // 执行图文分享
//                            wechat.share(sp);
//                            shareDialog.dismiss();
//                            Toast.makeText(PictureActivity.this, "正在分享，请稍候", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    showShareDialog();
//                }
//                shareDialog.show();

                showShare();
            }
        });
        car_picture_vp.setAdapter(new PictureViewPagerAdapter());
        car_picture_vp.setOnPageChangeListener(this);
        car_picture_vp.setCurrentItem(currentposition);
    }


    // 分享回调接口
    private class ShareListener implements PlatformActionListener {

        @Override
        public void onCancel(Platform arg0, int arg1) {
            // 判断当前是否是分享结果
            if (arg1 == Platform.ACTION_SHARE) {
                shareHandler.sendEmptyMessage(MSG_AUTH_CANCEL);
            }
        }

        @Override
        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
            // 判断当前是否是分享结果
            if (arg1 == Platform.ACTION_SHARE) {
                shareHandler.sendEmptyMessage(MSG_AUTH_COMPLETE);
            }
        }

        @Override
        public void onError(Platform arg0, int arg1, Throwable arg2) {
            // 判断当前是否是分享结果
            if (arg1 == Platform.ACTION_SHARE) {
                Log.e("tazz", arg2.getMessage());
                shareHandler.sendEmptyMessage(MSG_AUTH_ERROR);
            }
        }

    }


    @Override
    public int getLayoutID() {
        return R.layout.activity_pictrue;
    }


    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra("datas", bundle);
        context.startActivity(intent);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(final int i) {
        Log.e("taqqq", "执行了pageselected");
        car_picture_tv_index.setText((i + 1) + "/" + bmobFileurls.length);
        currentposition = i;

        BmobQuery<NewsTitle> query = new BmobQuery<NewsTitle>();
        query.getObject(objects[i], new QueryListener<NewsTitle>() {
            @Override
            public void done(NewsTitle newsTitle, BmobException e) {
                Log.e("taqqq", objects[i] + "");
                if (e == null) {
                    BmobQuery<_User> query1 = new BmobQuery<_User>();
                    query1.addWhereRelatedTo("likes", new BmobPointer(newsTitle));
                    query1.findObjects(new FindListener<_User>() {
                        @Override
                        public void done(List<_User> object, BmobException e) {
                            if (e == null) {
                                if (BmobUser.getCurrentUser(_User.class) != null) {
                                    Log.e("taqqq", "有用户");
                                    if (object.size() != 0) {
                                        for (int i = 0; i < object.size(); i++) {
                                            if (BmobUser.getCurrentUser(_User.class).getObjectId().equals(object.get(i).getObjectId())) {
                                                Log.e("taqqq", "有关系");
                                                car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nor);
                                                isFav = true;
                                            } else {
                                                Log.e("taqqq", "没关系");
                                                car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                                                isFav = false;
                                            }
                                        }
                                    } else {
                                        Log.e("taqqq", "没关联用户");
                                        car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                                        isFav = false;
                                    }
                                } else {
                                    Log.e("taqqq", "没用户");
                                    car_picture_iv_fav.setImageResource(R.drawable.ic_fav_nu_nor);
                                    isFav = false;
                                }
                            } else {
                                Log.i("bmob", "失败：" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private class PictureViewPagerAdapter extends BasePagerAdapter {


        @Override
        public int getCount() {
            return bmobFileurls.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(PictureActivity.this, R.layout.news_picture_item, null);
            ImageView picture_iv_item = (ImageView) view.findViewById(R.id.picture_iv_item);
            ImageLoader.getInstance().displayImage(bmobFileurls[position], picture_iv_item, Constant.options);
            container.addView(view);
            return view;
        }

    }


    private void showShareDialog() {
        shareDialog = new Dialog(this, R.style.DialogNoTitleStyleTranslucentBg);
        shareDialog.setContentView(choosedItem, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = shareDialog.getWindow();
        window.setWindowAnimations(R.style.BottomMenuAnim);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置起始位置
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        shareDialog.onWindowAttributesChanged(wl);
        // 设置点击外面关闭对话框
        shareDialog.setCanceledOnTouchOutside(true);
    }

    private void hideShareDialog() {
    }


    private View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    private boolean isFav = false;
    private ViewGroup anim_mask_layout;// 动画层

    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);// 把动画+1添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
//        start_location[0]=start_location[0]+DisplayUtil.dip2px(this, 20);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
//        end_location[0]=start_location[0]+DisplayUtil.dip2px(this, 20);
        end_location[1] = start_location[1] - DisplayUtil.dip2px(this, 30);

//        // 计算位移
//        int endX = end_location[0] - start_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
//        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
//        translateAnimationX.setInterpolator(new LinearInterpolator());
//        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
//        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
//        set.addAnimation(translateAnimationX);
        set.setDuration(500);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("tazz", "visi");
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("tazz", "gone");
                v.setVisibility(View.GONE);
            }
        });


    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        int i = Integer.MAX_VALUE;
        animLayout.setId(i);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setImageUrl(bmobFileurls[currentposition]);
// 启动分享GUI
        oks.show(this);
    }


}
