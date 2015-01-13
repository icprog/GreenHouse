package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.GreenHouseApplication;
import com.ryancat.greenhouseclient.databean.GatewayListDataBean;
import com.ryancat.greenhouseclient.network.HttpManager;
import com.ryancat.greenhouseclient.util.Constants;
import com.ryancat.greenhouseclient.util.L;

import android.content.Context;
import android.os.Handler;

/**
 * 列出网关列表的Task
 * @author RyanHu
 *
 */
final class ShowGatewayListTask extends BaseTask
{
	private static final int TASK_TAG = TaskConstants.SHOW_GATEWAY_TASK;
	public ShowGatewayListTask(Handler handler, Context _context)
	{
		super(handler, _context);
		
	}

	@Override
	public void run()
	{
		// 第二步登录成功，开始获取网关列表
		GatewayListDataBean gatewayListDataBean = new GatewayListDataBean();
		gatewayListDataBean.cid = GreenHouseApplication.cid;
		gatewayListDataBean.cToken = GreenHouseApplication.cToken;
		gatewayListDataBean.page = "1";
		gatewayListDataBean = (GatewayListDataBean) httpManager.requestServer(Constants.GwList_Url, gatewayListDataBean, true);
		if(gatewayListDataBean.status.equals(Constants.Status_Success))
		{
			L.d("Get Gateway success!");
			sendResultMessage(TASK_TAG, gatewayListDataBean, TaskConstants.TASK_SUCCESS, 0);
			
		}else
		{
			L.e("获取网关列表出错 ---->" + gatewayListDataBean.status);
			sendResultMessage(TASK_TAG, gatewayListDataBean, TaskConstants.TASK_FAILED, 0);

		}
	
	}
}
