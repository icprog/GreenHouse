package com.greenhouseclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;
import com.greenhouseclient.databean.MQTT_DetectorDataBean;
import com.greenhouseclient.databean.GatewayListDataBean;
import com.greenhouseclient.databean.MQTT_GwDataBean;
import com.greenhouseclient.databean.MQTTDataBeans;
import com.greenhouseclient.util.Constants;
import com.greenhouseclient.util.GreenHouseUtils;
import com.greenhouseclient.util.L;
import com.greenhouseclient.view.ShowDetectorActivity;
import com.greenhouseclient.view.ShowGatewayActivity;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MQTTService extends Service
{
	
	private MqttClient mClient; // 操作的基本对象
	private ClientCallback mClientCallback; // 回调函数
	private ClientOption mClientOption; // 连接参数
	private String myTopic = "/p/c/";
	private MyHandler mHandler;
	private ScheduledExecutorService scheduler;

	@Override
	public void onCreate()
	{
		super.onCreate();
		init();
		setCallbackAndAdapter();
		startReconnect();

	}

	/**
	 * 
	 * <p>
	 * Description:初始化界面和MqttClient的一些对象，主要是new
	 * </p>
	 * 
	 * @author RyanHu
	 * @date 2014年12月2日
	 */
	private void init()
	{
		try
		{
			GreenHouseUtils.acquireWakeLock(this);// 保持唤醒
			scheduler = Executors.newSingleThreadScheduledExecutor();
			mClientCallback = new ClientCallback();
			mHandler = new MyHandler();
			mClient = new MqttClient(Constants.mqtt_Host, String.valueOf(GreenHouseApplication.cid), new MemoryPersistence());
			mClientOption = new ClientOption();
			mClientOption.setCleanSession(true);// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			mClientOption.setConnectionTimeout(10); // 设置超时时间 单位为秒
			mClientOption.setKeepAliveInterval(20); // 设置会话心跳时间 单位为秒
													// 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制

		} catch (MqttException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Description:对象的适配操作
	 * </p>
	 * 
	 * @author RyanHu
	 * @date 2014年12月2日
	 */
	private void setCallbackAndAdapter()
	{
		mClient.setCallback(mClientCallback);
	}

	/**
	 * 
	 * <p>
	 * Title: ClientCallback
	 * </p>
	 * <p>
	 * Description:回调接口的一个实现类
	 * </p>
	 * 
	 * @author RyanHu
	 * @date 2014年12月2日
	 */
	private class ClientCallback implements MqttCallback
	{

		@Override
		public void connectionLost(Throwable arg0)
		{
			L.d("connectionLost----------");

		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token)
		{
			L.d("deliveryComplete---------" + token.isComplete());

		}

		@Override
		public void messageArrived(String topicName, MqttMessage message) throws Exception
		{
			// L.d("messageArrived----------"+message.toString());
			Message msg = new Message();
			msg.what = 1;
//			msg.obj = topicName + "---" + message.toString();
			msg.obj = message.toString();
			mHandler.sendMessage(msg);
		}
	}

	private class MyHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == 1)
			{
				Toast.makeText(MQTTService.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
				L.e("mqtt receive->" + msg.obj);

				try
				{
					String message = (String) msg.obj;
//					String match = "---";
//					message = message.split(match)[1];
					MQTTDataBeans mqttBean = new Gson().fromJson(message, MQTTDataBeans.class);// 获取到MQTT传递过来的数据，转化为MqttBean
					MQTT_GwDataBean[] gwDataBeans = mqttBean.param.gwList;
					for (int i = 0; i < gwDataBeans.length; i++)
					{
						int t_gwid = gwDataBeans[i].gwid;// 拿到网关id
						Map<Integer, ArrayList<MQTT_DetectorDataBean>> Datakeeper_decetorDataBeans = DataKeeper.All_GW_Collections_Minute.get(t_gwid);// 拿到网关对应的探头
						if (Datakeeper_decetorDataBeans == null)
						{
							Datakeeper_decetorDataBeans = new HashMap<Integer, ArrayList<MQTT_DetectorDataBean>>();
						}
						MQTT_DetectorDataBean[] mqtt_detectorDatabeans = gwDataBeans[i].detectorDatas;
						if (mqtt_detectorDatabeans != null && mqtt_detectorDatabeans.length != 0)
						{
							for (int j = 0; j < mqtt_detectorDatabeans.length; j++)
							{
								int decetorId = mqtt_detectorDatabeans[j].did;
								ArrayList<MQTT_DetectorDataBean> data_list = Datakeeper_decetorDataBeans.get(decetorId);
								if (data_list == null)
								{
									data_list = new ArrayList<MQTT_DetectorDataBean>();
								}
								// 开始赋值
								data_list.add(mqtt_detectorDatabeans[j]);
								if (data_list.size() > 60)
								{
									data_list.remove(0);
								}
								Datakeeper_decetorDataBeans.put(decetorId, data_list);
							}
						}
						DataKeeper.All_GW_Collections_Minute.put(t_gwid, Datakeeper_decetorDataBeans);
						if (ShowDetectorActivity.getHandler() != null)
						{
							Message t_msg = Message.obtain();
							t_msg.arg1 = t_gwid;
							ShowDetectorActivity.getHandler().sendMessage(t_msg);
						}
						if (ShowGatewayActivity.getHandler() != null)
						{
							Message t_msg = Message.obtain();
							t_msg.arg1 = t_gwid;
							ShowGatewayActivity.getHandler().sendMessage(t_msg);
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					L.e("推送数据类型转化异常！");
				}
				L.d("数据转化结束");
			} else if (msg.what == 2)
			{
				Toast.makeText(MQTTService.this, "连接成功", Toast.LENGTH_SHORT).show();
				try
				{
					mClient.subscribe(myTopic + GreenHouseApplication.cid, 1);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			} else if (msg.what == 3)
			{
				Toast.makeText(MQTTService.this, "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void connect()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					mClient.connect(mClientOption);
					Message msg = new Message();
					msg.what = 2;
					mHandler.sendMessage(msg);
				} catch (Exception e)
				{
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 3;
					mHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	private void startReconnect()
	{
		scheduler.scheduleAtFixedRate(new Runnable()
		{

			@Override
			public void run()
			{
				if (!mClient.isConnected())
				{
					connect();
				}
			}
		}, 0 * 1000, 1 * 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * <p>
	 * Title: ClientOption
	 * </p>
	 * <p>
	 * Description:参数接口
	 * </p>
	 * 
	 * @author RyanHu
	 * @date 2014年12月2日
	 */
	private class ClientOption extends MqttConnectOptions
	{

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		L.d("MQTT Service Destroy~~~");
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
