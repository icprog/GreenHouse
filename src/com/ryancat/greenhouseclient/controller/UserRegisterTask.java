package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.GreenHouseApplication;
import com.ryancat.greenhouseclient.databean.RegisterDataBean;
import com.ryancat.greenhouseclient.network.HttpManager;
import com.ryancat.greenhouseclient.util.Constants;
import com.ryancat.greenhouseclient.util.L;

import android.content.Context;
import android.os.Handler;

public class UserRegisterTask extends BaseTask
{
	private static final int TASK_TAG = TaskConstants.USER_LOGIN_TASK;

	public UserRegisterTask(Handler handler, Context _context)
	{
		super(handler, _context);
	}

	@Override
	public void run()
	{
		// 首先用手机信息换取cid
		RegisterDataBean registerDataBean = new RegisterDataBean();
		registerDataBean = (RegisterDataBean) httpManager.requestServer(Constants.Register_Url, registerDataBean, true);
		if (registerDataBean.status.equals(Constants.Status_Success))
		{
			L.d("Register success!");
			GreenHouseApplication.cid = registerDataBean.cid;
			sendResultMessage(TASK_TAG, registerDataBean, TaskConstants.TASK_SUCCESS, 0);
		} else
		{
			// 注册接口出错
			L.e("注册接口出错 ---->" + registerDataBean.status);
			sendResultMessage(TASK_TAG, registerDataBean, TaskConstants.TASK_FAILED, 0);

		}
	}

}
