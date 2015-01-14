package com.ryancat.greenhouseclient.databean;

import com.ryancat.greenhouseclient.annotation.HttpAnnotation;
import com.ryancat.greenhouseclient.util.Constants;

/**
 * 注册接口上传的DataBean
 * 没有上传自有参数
 * 返回有自有参数
 * @author RyanHu
 *
 */
public final class RegisterDataBean  extends BaseDataBean
{
	//返回参数，客户端ID
	@HttpAnnotation(HttpType =Constants.Http_RESPONSE)
	public int cid ;
}
