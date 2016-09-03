package com.chenhong.android.carsdoor.utils;

import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;

/**
 * ��������Ӧ�õ�activity��ջ
 * @author Android
 *
 */
public class ActivityStack {
	
	private static ActivityStack mSingleInstance;
	private Stack<Activity> mActicityStack;
	
	//����
	
	
	private ActivityStack(){
		mActicityStack=new Stack<Activity>();
	}
	
	
	public static ActivityStack getInstace(){
		if(mSingleInstance==null){
			mSingleInstance=new ActivityStack();
		}
		return mSingleInstance;	
	}
	
	public Stack<Activity> getStack()
	{
		return mActicityStack;
	}
	
	
	////////////////////////////////////////////
	
	
	

	/**
	 * ��ջ
	 */
	public void addActivity(Activity activity)
	{
		mActicityStack.push(activity);
	}
	
	/**
	 * ��ջ
	 */
	public void removeActivity(Activity activity)
	{
		mActicityStack.remove(activity);
	}

	
	/**
	 * �����˳�
	 */
	public void finishAllActivity()
	{
		Activity activity;
		while (!mActicityStack.empty())
		{
			activity = mActicityStack.pop();
			if (activity != null)
				activity.finish();
		}
	}
	
	
	/**
	 * finishָ����activity
	 */
	public boolean finishActivity(Class<? extends Activity> actClz)
	{
		Activity act = findActivityByClass(actClz);
		if (null != act && !act.isFinishing())
		{
			act.finish();
			return true;
		}
		return false;
	}


	private Activity findActivityByClass(Class<? extends Activity> actClz) {
		// TODO Auto-generated method stub
		
		Activity activity=null;
		
		Iterator<Activity> iterator=mActicityStack.iterator();
		//�õ������
		
		
		while(iterator.hasNext()){//����
			
			activity=iterator.next();
			//���activity��Ϊ���Ҳ������ڱ������ �ҷ���ֽ�������ͬ
			if (null != activity && activity.getClass().getName().equals(actClz.getName()) && !activity.isFinishing())
			{
				break;//�����ֵ�ɹ�
			}
			activity=null;
		}
		
		return activity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
