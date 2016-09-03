package com.chenhong.android.carsdoor.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.CityAdapter;
import com.chenhong.android.carsdoor.entity.City;
import com.chenhong.android.carsdoor.entity._User;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.QuickIndexBar;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ChooseCityActivity extends BaseActivity implements QuickIndexBar.onTouchLetterListener{


    @ViewInject(R.id.qib)
    private QuickIndexBar quickIndexBar;
    @ViewInject(R.id.lv_content)
    private ListView lv_content;
    @ViewInject(R.id.currentindex)
    private TextView currentindex;
    private Handler handler=new Handler(){
    };
    private String sql="select * from T_city";
    private String f;   //数据库存入手机的路径
    private List<City> citys = new ArrayList<>();
    private CityAdapter adapter;
    City city;

    @Override
    protected void initView() {
        quickIndexBar.setonTouchListener(this);




            new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                //将raw目录下的db文件，存入<包名>/databases/目录下
                writeDB();
                Log.d("taqqq", "DB uri : "+ getDatabasePath("a1.db"));
                Log.d("taqqq", f);
                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(f, MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.query("T_city", null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        String cityname =  cursor.getString(cursor.getColumnIndex("CityName"));//获取第二列的值
                        String namesort =  cursor.getString(cursor.getColumnIndex("NameSort"));//获取第三列的值
                        Log.d("taqqq", "_id : " + cityname + " , name : " + namesort);
                        citys.add(new City(cityname,namesort));
                    }
                cursor.close();
                sqLiteDatabase.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Collections.sort(citys);
                if(adapter==null) {
                    adapter = new CityAdapter(ChooseCityActivity.this, citys);
                    lv_content.setAdapter(adapter);
                }else {
                    adapter.refreshDatas(citys);
                    adapter.notifyDataSetChanged();
                }
                quickIndexBar.setVisibility(View.VISIBLE);
            }
        }.execute();



        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = citys.get(position);
                final SpotsDialog spotsDialog = new SpotsDialog(ChooseCityActivity.this);
                spotsDialog.show();
                _User user=new _User();
                user.setCity(city.getCityName());
                _User currentUser = BmobUser.getCurrentUser(_User.class);
                user.update(currentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            MyUtils.showToast(ChooseCityActivity.this,"保存成功");
                            spotsDialog.dismiss();
                            finish();
                        }else {
                            Log.e("tazzz",e.getMessage());
                            spotsDialog.dismiss();
                            MyUtils.showToast(ChooseCityActivity.this,"保存失败");
                        }
                    }
                });

            }
        });

    }



    @Override
    public int getLayoutID() {
        return R.layout.activity_choose;
    }

    @Override
    public void onTouchLetter(String letter) {
        //根据触摸的字母去集合中找到那个item然后放到顶端
        for (int i = 0; i < citys.size(); i++) {
            String firstletter = citys.get(i).getNameSort().charAt(0) + "";
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


    public void writeDB(){
        f = getFilesDir()+"\\databases\\"+"a1.db";  //此处如果是放在应用包名的目录下,自动放入“databases目录下
        FileOutputStream fout = null;
        InputStream inputStream = null;
        try {
            inputStream = getResources().openRawResource(R.raw.china_city_name);
            fout = new FileOutputStream(new File(f));
            byte[] buffer = new byte[128];
            int len = 0;
            while ((len = inputStream.read(buffer))!=-1){
                fout.write(buffer, 0, len);
            }
            buffer = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
