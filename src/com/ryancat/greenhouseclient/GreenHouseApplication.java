package com.ryancat.greenhouseclient;

import com.ryancat.greenhouseclient.controller.ClientController;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 继承了Application类，为整个应用唯一的application类。
 * 应用全局级别的filed和method应在此或者此类的拓展接口有所体现,比如SharedPreferences什么的。
 * 本类不应涉及界面或者业务。
 * @author RyanHu
 *
 */
public class GreenHouseApplication extends Application
{
	//TEST
	//TEST KIRIGIRIKYOKO
	/**控制器**/
	private ClientController mClientController;
	@Override
	public void onCreate()
	{
		super.onCreate();
		init();
	}
	
	/**
	 * 初始化函数，进入程序时调用。
	 */
	private void init ()
	{
		mClientController = ClientController.getInstance(this);
	}
	/**
	 * 拿到控制器
	 * @return
	 */
	public ClientController getClientController()
	{
		//
		return mClientController;
	}
	
}
