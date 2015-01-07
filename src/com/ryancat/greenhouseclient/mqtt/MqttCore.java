package com.ryancat.greenhouseclient.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.os.Handler;
import android.os.Message;

/**
 * Mqtt的推送核心类 需要连接推送服务器的功能和接收服务器推送消息的功能，并对外暴露调用接口。
 * 
 * @author RyanHu
 * 
 */
public class MqttCore
{
	private String mServerAddress;// 服务器地址
	private String mTopic;// 订阅的主题
	private ClientCallback mCallback;// Mqtt回调函数
	private MqttHandler mHandler;
	private MqttConnectOptions mOptions;//参数
	private MqttMsgListener mListener;//给上层的回调函数
	

	private static final int MQTT_CONNECTIONLOST = -1;//断开连接
	private static final int MQTT_DELIVERCOMPLETE = 0;//消息送达
	private static final int MQTT_MSGARRIVED = 1;//收到推送

	
	private static MqttCore sInstance = new MqttCore();

	private MqttCore()
	{

	}

	public static MqttCore getInstance()
	{
		return sInstance;
	}

	/**
	 * 初始化函数
	 */
	private void init()
	{
		mHandler = new MqttHandler();
		mCallback = new ClientCallback();
	}

	/**
	 * 提供连接服务器的函数
	 */
	public void connectService()
	{

	}

	/**
	 * 设置推送服务器地址
	 * @param address
	 */
	public void setServerAddress(String address)
	{
		mServerAddress = address;
	}
	/**
	 * 设置订阅的主题
	 * @param topic
	 */
	public void setTopic(String topic)
	{
		mTopic = topic;
	}
	/**
	 * 设置回调函数
	 * @param listener
	 */
	public void setListener (MqttMsgListener listener)
	{
		mListener = listener;
	}
	/**
	 * MQTT的回调函数，因为messageArrived函数是在子线程被调用，所以需要通过handler的形式与上层通讯。
	 * 
	 * @author RyanHu
	 */
	private class ClientCallback implements MqttCallback
	{

		@Override
		public void connectionLost(Throwable arg0)
		{
			Message msg = Message.obtain();
			msg.what = MQTT_CONNECTIONLOST;
			msg.obj = arg0;
			mHandler.sendMessage(msg);
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0)
		{
			Message msg = Message.obtain();
			msg.what = MQTT_DELIVERCOMPLETE;
			msg.obj = arg0;
			mHandler.sendMessage(msg);
		}

		@Override
		public void messageArrived(String arg0, MqttMessage arg1) throws Exception
		{
			MessageBean msgbean = new MessageBean(arg0, arg1);
			Message msg = Message.obtain();
			msg.what = MQTT_MSGARRIVED;
			msg.obj = msgbean;
			mHandler.sendMessage(msg);

		}
	}

	/**
	 * 接受消息的实体
	 * 
	 * @author RyanHu
	 * 
	 */
	private class MessageBean
	{
		String topic;
		MqttMessage message;

		public MessageBean(String topic, MqttMessage message)
		{
			super();
			this.topic = topic;
			this.message = message;
		}

	}

	/**
	 * Mqtt自身回调函数与上层回调函数通讯的handler
	 * @author RyanHu
	 *
	 */
	private class MqttHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case MQTT_CONNECTIONLOST:
				
				break;
			case MQTT_DELIVERCOMPLETE:

				break;
			case MQTT_MSGARRIVED:
				MessageBean mb = (MessageBean) msg.obj;
				mListener.onMessageArrived(mb.topic, mb.message);
				break;
			default:
				break;
			}

		}
	}
}
