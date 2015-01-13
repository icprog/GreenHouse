package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;
import com.ryancat.greenhouseclient.util.Constants;

public class GatewayListDataBean extends BaseDataBean
{
	//客户端唯一ID标识
	@HttpAnnotation
	public String cid ;
	//客户端合法token
	@HttpAnnotation
	public String cToken;
	//页码
	@HttpAnnotation
	public String page;
	
	//所有条目数
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public int allRow;
	//所有页码数量
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public int totalPage;
}
