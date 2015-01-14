package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;
import com.ryancat.greenhouseclient.util.Constants;

/**
 * 获取网关列表时候用到的Bean
 * @author RyanHu
 *
 */
public final class GatewayListDataBean extends BaseDataBean
{
	//客户端唯一ID标识
	@HttpAnnotation
	public int cid ;
	//客户端合法token
	@HttpAnnotation
	public String cToken;
	//页码
	@HttpAnnotation
	public int page;
	
	
	//所有条目数
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public int allRow;
	//所有页码数量
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public int totalPage;
	//网关列表
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public GatewayBean[] gwList;
}
