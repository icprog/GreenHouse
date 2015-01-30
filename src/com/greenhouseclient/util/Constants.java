package com.greenhouseclient.util;

/**
 * 保存常量的，比如服务器地址，编码什么的。
 * 
 * @author RyanHu
 * 
 */
public interface Constants
{

	String ENCODING = "utf-8";
	String HTTP_REQUEST = "Http_Request";
	String Http_RESPONSE = "Http_Response";
	String Status_Success = "success";

	String Server_Address = "http://yirenna.com:8081";
	String Register_Url = Server_Address + "/dp/client/reg.do?";// 注册地址
	String Login_Url = Server_Address + "/dp/client/login.do?";// 登录地址
	String GwList_Url = Server_Address + "/dp/client/gwList.do?";// 网关列表地址
	String AddGw_Url = Server_Address + "/dp/client/addMonitor.do?";// 添加或者删除网关地址

	int ACTION_GATEWAY_ADD = 1;// 添加网关动作
	int ACTION_GATEWAY_DEL = 0;// 删除网关动作

	int TEMP_MAX =100;
	int BEAM_MAX =100;
	int HUMI_MAX =100;
	int TEMP_MIN = 0;
	int BEAM_MIN = 0;
	int HUMI_MIN = 0;
}
