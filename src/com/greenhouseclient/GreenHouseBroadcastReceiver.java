package com.greenhouseclient;

import com.greenhouseclient.util.L;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 广播接收器，监听网络状态变化的
 * @author RyanHu
 *
 */
public class GreenHouseBroadcastReceiver extends BroadcastReceiver
{
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
		{
			L.d("网络状态已经改变");
			connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			info = connectivityManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable())
			{
				String name = info.getTypeName();
				GreenHouseApplication.IsNetworkConnected =true;
				GreenHouseApplication.Ap = name;
				L.d( "当前网络名称：" + name);
			} else
			{
				GreenHouseApplication.IsNetworkConnected =false;
				L.d( "没有可用网络");
			}
		}
	}

}
