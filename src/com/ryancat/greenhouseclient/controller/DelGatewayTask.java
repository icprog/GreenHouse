package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.GreenHouseApplication;
import com.ryancat.greenhouseclient.databean.AddGatewayBean;
import com.ryancat.greenhouseclient.util.Constants;

import android.content.Context;
import android.os.Handler;

/**
 * 删除一个网关的task
 * @author RyanHu
 *
 */
final class DelGatewayTask extends BaseTask
{
	private static final int TASK_TAG  = TaskConstants.DEL_GATEWAY_TASK;
	private int gwid ;
	public DelGatewayTask(Handler handler, Context _context , int _gwid)
	{
		super(handler, _context);
		gwid = _gwid;
	}

	@Override
	public void run()
	{
		AddGatewayBean addGatewayBean = new AddGatewayBean();
		addGatewayBean.cid = GreenHouseApplication.cid;
		addGatewayBean.cToken = GreenHouseApplication.cToken;
		addGatewayBean.gwid = gwid;
		addGatewayBean.action = Constants.ACTION_GATEWAY_DEL;
		addGatewayBean = (AddGatewayBean) httpManager.requestServer(Constants.AddGw_Url, addGatewayBean, true);
		if(addGatewayBean.status.equals(Constants.Status_Success))
		{
			sendResultMessage(TASK_TAG, addGatewayBean,TaskConstants.TASK_SUCCESS,0);
		}
		else
		{
			sendResultMessage(TASK_TAG, addGatewayBean,TaskConstants.TASK_FAILED,0);
		}
	}

}
