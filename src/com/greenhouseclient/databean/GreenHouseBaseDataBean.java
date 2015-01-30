package com.greenhouseclient.databean;

/**
 * 封装温度光照湿度的databean 此DataBean应当用在MQTT的推送中，不是标准的HTTP DATABEAN
 * 
 * @author RyanHu
 * 
 */
public class GreenHouseBaseDataBean
{
	public int beam;
	public int gwid;
	public int humidity;
	public String message;
	public String name;
	public int temperature;
	public int warning;
}
