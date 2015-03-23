package com.greenhouseclient.databean;
/**
 * 网关数据bean，封装了好些个探头
 * @author RyanHu
 *
 */
public class MQTT_GwDataBean
{
	public int gwid;//网关id
	public String message ;//网关信息
	public String name ;//网关名称
	public String warning;//警告类型
	public MQTT_DetectorDataBean[] detectorDatas;//网关上探头的数据
}
