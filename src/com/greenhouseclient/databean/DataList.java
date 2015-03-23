package com.greenhouseclient.databean;

public class DataList
{
	//序号
	public int num;
	//日志时间
	public String logTime;
	//网关是否警告
	public int waring;
	//网关警告描述
	public String message;
	//探头数据
	public DetectorDatas[] detectorDatas;
}
