package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;

/**
 * 增加一个网关或者删除一个网关的DataBean
 * @author RyanHu
 *
 */
public class AddGateWayDataBean extends BaseDataBean
{
	//客户端唯一ID
	@HttpAnnotation
	public String cid ;

	//客户端的token
	@HttpAnnotation
	public String cToken;
	
	//目标网关的id
	@HttpAnnotation
	public String gwid;
	
	//操作，1添加0删除
	@HttpAnnotation
	public String action; 
}
