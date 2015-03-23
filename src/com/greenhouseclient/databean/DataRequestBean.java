package com.greenhouseclient.databean;

import com.greenhouseclient.annotation.HttpAnnotation;
import com.greenhouseclient.util.Constants;

public class DataRequestBean extends BaseDataBean
{
	//客户端id
	@HttpAnnotation
	public int cid;
	//客户端token
	@HttpAnnotation
	public String cToken;
	//网关id
	@HttpAnnotation
	public int gwid;
	//开始时间
	@HttpAnnotation
	public long startTime;
	//结束时间
	@HttpAnnotation
	public long endTime;
	//获取数据
	@HttpAnnotation(HttpType =Constants.Http_RESPONSE)
	public DataList[] dataList;
	
}
