package com.greenhouseclient.controller;

import com.greenhouseclient.GreenHouseApplication;
import com.greenhouseclient.databean.AddGatewayBean;
import com.greenhouseclient.util.Constants;

import android.content.Context;
import android.os.Handler;

/**
 * 增加一个网关的task
 * @author RyanHu
 *
 */
final class AddGatewayTask extends BaseTask
{
	private static final int TASK_TAG  = TaskConstants.ADD_GATEWAY_TASK;
	private int gwid ;
	public AddGatewayTask(Handler handler, Context _context , int _gwid)
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
		addGatewayBean.action = Constants.ACTION_GATEWAY_ADD;
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
