package com.greenhouseclient;

import com.greenhouseclient.controller.ClientController;
import com.greenhouseclient.databean.GatewayListDataBean;
import com.greenhouseclient.util.GreenHouseUtils;
import com.greenhouseclient.util.L;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * 继承了Application类，为整个应用唯一的application类。
 * 应用全局级别的filed和method应在此或者此类的拓展接口有所体现,比如SharedPreferences什么的。 本类不应涉及界面或者业务。
 * 
 * @author RyanHu
 * 
 */
public class GreenHouseApplication extends Application
{
	public static boolean IsNetworkConnected;

	public static String IMEI;
	public static String Mac;
	public static String Ua;
	public static String Ap;
	public static String Ver;
	public static String SysVer;
	public static int cid;
	public static String cToken;
	public static GatewayListDataBean gatawayListBean;//网关总条目数
	/** 控制器 **/
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
	private void init()
	{
		L.isOpenLog(true);
		mClientController = ClientController.getInstance(this);
		initFiled();
		setInternetStateReceive();
		IsNetworkConnected = GreenHouseUtils.isNetworkConnected(this);
	}

	/**
	 * 拿到控制器
	 * 
	 * @return
	 */
	public ClientController getClientController()
	{
		return mClientController;
	}

	/**
	 * 这里对Http的几个透传参数进行赋值 因为是在程序一开始的地方进行初始化，只进行一次赋值操作
	 */
	private void initFiled()
	{
		IMEI = GreenHouseUtils.getIMEI(this);
		Mac = GreenHouseUtils.getMac(this);
		Ua = android.os.Build.MODEL;
		SysVer = android.os.Build.VERSION.RELEASE;
		Ver = GreenHouseUtils.getAppVersionName(this);
		Ap = GreenHouseUtils.getAccessPointName(this);
	}

	/**
	 * 设置全局网络监听广播
	 */
	private void setInternetStateReceive()
	{
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(new GreenHouseBroadcastReceiver(), mFilter);
	}

}
