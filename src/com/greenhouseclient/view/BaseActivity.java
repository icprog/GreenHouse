package com.greenhouseclient.view;

import com.greenhouseclient.GreenHouseApplication;
import com.greenhouseclient.controller.ClientController;
import com.greenhouseclient.util.L;
import com.greenhouseclient.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity的基类
 * 
 * @author RyanHu
 * 
 */
public abstract class BaseActivity extends Activity implements OnClickListener
{
	/** 显示的view **/
	private ViewGroup mBackgroundView;
	private ViewGroup containerView;
	protected ViewGroup showView;
	protected ClientController controller;
	protected GreenHouseApplication mApp;
	protected Handler taskHandler;
	protected ImageButton mMenuImageButton;// 右上方的imageButton
	protected TextView mNetworkStatusTV;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		L.d(this.getClass().getSimpleName() + " --->" + "onCreate!");
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		mBackgroundView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.container_activity, null);
		mMenuImageButton = (ImageButton) mBackgroundView.findViewById(R.id.title_menu_imgbtn);// 添加右上角的image
		setContentView(mBackgroundView);
		containerView = (ViewGroup) mBackgroundView.findViewById(R.id.container_layout);
		mNetworkStatusTV = (TextView)mBackgroundView.findViewById(R.id.netowrk_status);
		if(GreenHouseApplication.cToken!=null)
			mNetworkStatusTV.setText("已登录到服务器");
		else
			mNetworkStatusTV.setText("未登录到服务器");
		initViews();
		init();
		if (showView!=null &&showView.getLayoutParams() == null)
		{
			showView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}
		containerView.addView(showView);

	}

	@Override
	protected void onStart()
	{
		super.onStart();
		L.d(this.getClass().getSimpleName() + " --->" + "onStart!");
		setTaskHandler();
		progressLogic();

	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		L.d(this.getClass().getSimpleName() + " --->" + "onRestart!");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		L.d(this.getClass().getSimpleName() + " --->" + "onResume!");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		L.d(this.getClass().getSimpleName() + " --->" + "onPause!");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		L.d(this.getClass().getSimpleName() + " --->" + "onStop");

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		L.d(this.getClass().getSimpleName() + " --->" + "onDestroy");

	}

	/**
	 * 此函数需要给showView赋值
	 */
	protected abstract void initViews();

	private void init()
	{
		mApp = (GreenHouseApplication) getApplication();
		controller = mApp.getClientController();
	}

	/**
	 * 初始化逻辑部分的函数，放一些进入界面时就必须要实现的函数。 会在onStart的时候调用
	 */
	protected abstract void progressLogic();

	/**
	 * 设置执行task时的回调handler 需要实现一个handler给return回去 需要对taskHandler赋值
	 * 
	 * @param h
	 * @return
	 */
	protected abstract void setTaskHandler();

	protected Handler getTaskHandler()
	{
		return taskHandler;
	}
	/**
	 * 使用action启动Activity
	 * @param action
	 */
	protected void startActivityByAction(String action)
	{
		Intent t_intent =new Intent(action);
		startActivity(t_intent);
	}
	/**
	 * 使用class启动activity
	 * @param clazz
	 */
	protected void startActivityByName(Class clazz)
	{
		Intent t_intent =new Intent(BaseActivity.this,clazz);
		startActivity(t_intent);
	}
}
