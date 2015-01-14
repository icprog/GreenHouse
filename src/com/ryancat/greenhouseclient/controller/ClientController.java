package com.ryancat.greenhouseclient.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.ryancat.greenhouseclient.databean.GatewayListDataBean;
import com.ryancat.greenhouseclient.util.L;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * 业务逻辑的控制类。 该业务函数负责提供界面所需数据函数的API
 * 
 * @author RyanHu
 */
public class ClientController 
{

	private static ClientController sInstance;// 单例
	private ExecutorService mExecutorService;//线程池
	private final static int THREAD_NUM = 1;//线程数
	private Context mContext;

	private ClientController()
	{
		mExecutorService = Executors.newFixedThreadPool(THREAD_NUM);
	}
	public static ClientController getInstance(Context _context)
	{
		if(sInstance == null)
		{
			sInstance = new ClientController();
		}
		sInstance.mContext = _context;
		return sInstance;
	}
	/**
	 * 用户注册的外部API
	 */
	public void userRegister(Handler taskHandler)
	{
		mExecutorService.execute(new UserRegisterTask(taskHandler,mContext));
	}

	/**
	 * 用户登录的外部API
	 */
	public void userLogin(Handler taskHandler)
	{
		mExecutorService.execute(new UserLoginTask(taskHandler,mContext));

	}

	/**
	 * 增加一个网关的外部API
	 */
	public void addGateway(Handler taskHandler,int gwid)
	{
		mExecutorService.execute(new AddGatewayTask(taskHandler,mContext,gwid));
	}
	/**
	 * 删除一个网关的外部API
	 */
	public void delGateway(Handler taskHandler,int gwid)
	{
		mExecutorService.execute(new DelGatewayTask(taskHandler,mContext,gwid));
	}

	/**
	 * 列出网关列表的API
	 */
	public void showGatewayList(Handler taskHandler)
	{
		mExecutorService.execute(new ShowGatewayListTask(taskHandler,mContext));
	}
	/**
	 * 开启MQTT的API
	 */
	public void startMqttClient(Handler taskHandler)
	{
		mExecutorService.execute(new StartMqttTask(taskHandler,mContext));
	}
	/**
	 * 判断网关列表是否为空
	 * @param gatewayList
	 * @return
	 */
	public boolean isGatewayListEmpty(Object gatewayList)
	{
		GatewayListDataBean gwl_databean = (GatewayListDataBean)gatewayList;
		if(gwl_databean.allRow == 0)
		{
			L.e("网关列表为空！");
			return false;
		}
		L.e("网关列表不为空！");
		return true;
	}
	
}
