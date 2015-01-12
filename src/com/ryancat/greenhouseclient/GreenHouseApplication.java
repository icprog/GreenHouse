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
	public static String IMEI ;
	public static String Mac;
	public static String Ua;
	public static String Ap;
	public static String Ver;
	public static String SysVer;
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
		initFiled();
	}
	/**
	 * 拿到控制器
	 * @return
	 */
	public ClientController getClientController()
	{
		return mClientController;
	}
	/**
	 * 这里对Http的几个透传参数进行赋值
	 * 因为是在程序一开始的地方进行初始化，只进行一次赋值操作
	 */
	private void initFiled()
	{
		
	}
	
}
