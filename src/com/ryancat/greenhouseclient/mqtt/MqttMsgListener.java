package com.ryancat.greenhouseclient.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT回调函数
 * @author RyanHu
 *
 */
public interface MqttMsgListener
{
	public void onMessageArrived(String topic, MqttMessage message);
}
