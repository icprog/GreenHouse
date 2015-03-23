package com.greenhouseclient.controller;

/**
 * Task的对应接口
 * @author RyanHu
 *
 */
public interface TaskConstants
{
	public final static int TASK_SUCCESS = 0;//任务执行的结果，成功
	public final static int TASK_FAILED = -1;//任务执行的结果，失败

	public final static int USER_REGISTER_TASK = 10;//用户使用IMEI和MAC换CID的task
	public final static int USER_LOGIN_TASK = 11;//用户登录获取CTOKEN的task
	public final static int ADD_GATEWAY_TASK = 12;//添加一个网关的task
	public final static int DEL_GATEWAY_TASK = 12;//删除一个网关的task
	public final static int SHOW_GATEWAY_TASK = 14;//显示网关列表的task
	public final static int START_MQTT_TASK = 15;//开始MQTT任务的task
	public static final int GET_DATA_BY_MINUTE_TASK = 21;//按起止分钟获取数据的task
	public static final int GET_DATA_BY_HOUR_TASK = 22;//按起止小时获取数据的task
	public static final int GET_DATA_BY_DATE_TASK = 23;//获取周数据的task
}
