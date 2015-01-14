package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;
import com.ryancat.greenhouseclient.util.Constants;
/**
 * 登陆接口传递的DataBean
 * @author RyanHu
 *
 */
public final class LoginDataBean extends BaseDataBean
{
	//客户端唯一ID
	@HttpAnnotation
	public int cid ;
	
	//客户端当前合法token
	@HttpAnnotation(HttpType = Constants.Http_RESPONSE)
	public String cToken ; 
}
