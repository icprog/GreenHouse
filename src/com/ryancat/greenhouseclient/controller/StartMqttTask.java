package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.mqtt.MqttCore;

import android.content.Context;
import android.os.Handler;

/**
 * 开启MQTT推送服务的task 
 * @author RyanHu
 *
 */
final class StartMqttTask extends BaseTask
{
	public StartMqttTask(Handler handler, Context _context)
	{
		super(handler, _context);
	}

	private MqttCore mMqttCore;

	@Override
	public void run()
	{
//		mMqttCore.setServerAddress(address)
//		mMqttCore.setListener(listener)
//		mMqttCore.connectService()
		
//		sendResultMessage(what, obj, arg1, arg2)
	}

}
