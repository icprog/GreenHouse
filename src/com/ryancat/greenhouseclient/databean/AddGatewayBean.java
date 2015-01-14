package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;
/**
 * 添加或者删除网关时候用到的Bean
 * @author RyanHu
 *
 */
public final class AddGatewayBean extends BaseDataBean
{
	//客户端ID
	@HttpAnnotation
	public int cid;
	
	//客户端Token
	@HttpAnnotation
	public String cToken;
	
	//网关id
	@HttpAnnotation
	public int gwid;
	
	//操作，1添加0删除
	@HttpAnnotation
	public int action;
}
