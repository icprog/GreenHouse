package com.ryancat.greenhouseclient.databean;

/**
 * 封装温度光照湿度的databean
 * 此DataBean应当用在MQTT的推送中，不是标准的HTTP DATABEAN
 * @author RyanHu
 *
 */
public class GreenHouseDataBean extends BaseDataBean
{
	private String humidity;
	private String temperature;
	private String light;
	public String getHumidity()
	{
		return humidity;
	}
	public void setHumidity(String humidity)
	{
		this.humidity = humidity;
	}
	public String getTemperature()
	{
		return temperature;
	}
	public void setTemperature(String temperature)
	{
		this.temperature = temperature;
	}
	public String getLight()
	{
		return light;
	}
	public void setLight(String light)
	{
		this.light = light;
	}
	
}
