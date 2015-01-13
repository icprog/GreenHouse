package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.GreenHouseApplication;
import com.ryancat.greenhouseclient.databean.GatewayListDataBean;
import com.ryancat.greenhouseclient.databean.LoginDataBean;
import com.ryancat.greenhouseclient.databean.RegisterDataBean;
import com.ryancat.greenhouseclient.network.HttpManager;
import com.ryancat.greenhouseclient.util.Constants;
import com.ryancat.greenhouseclient.util.L;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 用户登录的task
 * 
 * @author RyanHu
 * 
 */
final class UserLoginTask extends BaseTask
{

	private static final int TASK_TAG = TaskConstants.USER_LOGIN_TASK;

	public UserLoginTask(Handler handler, Context _context)
	{
		super(handler, _context);
	}

	@Override
	public void run()
	{
			LoginDataBean loginDataBean = new LoginDataBean();
			loginDataBean.cid = GreenHouseApplication.cid;
			loginDataBean = (LoginDataBean) httpManager.requestServer(Constants.Login_Url, loginDataBean, true);
			if (loginDataBean.status.equals(Constants.Status_Success))
			{
				L.d("login success!");
				L.d("loginDataBean is " +loginDataBean.toString());
				GreenHouseApplication.cToken = loginDataBean.cToken;
				sendResultMessage(TASK_TAG, loginDataBean, TaskConstants.TASK_SUCCESS, 0);
			} else
			{
				L.e("登录接口出错 ---->" + loginDataBean.status);
				sendResultMessage(TASK_TAG, loginDataBean, TaskConstants.TASK_FAILED, 0);
			}
	}
}
