package com.greenhouseclient.databean;

/**
 * 探头的dataBean ，目前用在HTTP里
 * @author RyanHu
 *
 */
public class DetectorDatas
{
	//探头id
	public int did;
	//探头名称
	public String name;
	//警告值
	public int waring;
	//警告的内容
	public String message;
	//温度平均
	public double temperature;
	//湿度平均
	public double humidity;
	//照度平均
	public double beam;
	//最大温度
	public double maxTemperature;
	//最低温度
	public double minTemperaturec;
	//最大湿度
	public double maxHumidity;
	//最小湿度
	public double minHumidity;
	//最大照度
	public double maxBeam;
	//最小照度
	public double minBeam;
	
}
