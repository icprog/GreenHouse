package com.greenhouseclient.controller;

import com.greenhouseclient.GreenHouseApplication;
import com.greenhouseclient.databean.RegisterDataBean;
import com.greenhouseclient.network.HttpManager;
import com.greenhouseclient.util.Constants;
import com.greenhouseclient.util.L;

import android.content.Context;
import android.os.Handler;

final class UserRegisterTask extends BaseTask
{
	private static final int TASK_TAG = TaskConstants.USER_REGISTER_TASK;

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
