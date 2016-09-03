package com.chenhong.android.carsdoor.factory;


import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsBuyFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsCarFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsImportantFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsMarketFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsPictureFragment;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsTryFragment;
import com.chenhong.android.carsdoor.fragment.selffragments.LoginFragment;
import com.chenhong.android.carsdoor.fragment.selffragments.LoginSecondFragment;
import com.chenhong.android.carsdoor.global.Constant;

public class FragmentFactory {

	 public static BaseFragment newInstance(int type) {
		         BaseFragment fragment=null;
		         switch(type){
		         case Constant.NEWSBUY:
		        	 fragment=new NewsBuyFragment();
		         break;
		         case Constant.NEWSCAR:
		        	 fragment=new NewsCarFragment();
		         break;
		         case Constant.NEWSIMP:
		        	 fragment=new NewsImportantFragment();
		         break;
		         case Constant.NEWSMAR:
		        	 fragment=new NewsMarketFragment();
		         break;
		         case Constant.NEWSPIC:
		        	 fragment=new NewsPictureFragment();
		         break;
		         case Constant.NEWSTRY:
		        	 fragment=new NewsTryFragment();
		         break;
				 case Constant.LOGIN_ONE:
					 fragment=new LoginFragment();
					 break;
				case Constant.LOGIN_TWO:
				 fragment=new LoginSecondFragment();
				 break;


		         }
		         
		        
		         return fragment;
		         
		         
		     }
	
	
	
	
	
	
	
	
	
	
	
}
