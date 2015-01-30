package com.greenhouseclient.controller;

import com.greenhouseclient.MQTTService;
import com.greenhouseclient.mqtt.MqttCore;

import android.content.Context;
import android.content.Intent;
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
		Intent i = new Intent(context, MQTTService.class);
		context.startService(i);

	}

}
