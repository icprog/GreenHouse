package com.ryancat.greenhouseclient.controller;

import com.ryancat.greenhouseclient.network.HttpManager;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 用户自服务注册的task
 * 
 * @author RyanHu
 *
 */
final class UserRegisterTask extends BaseTask
{
	public UserRegisterTask(Handler handler, Context _context)
	{
		super(handler, _context);
	}

	private HttpManager httpManager ;

	@Override
	public void run()
	{
		httpManager = HttpManager.getInstance();
		sendResultMessage(0, "UserRegister Task Finsh", 0, 0);
	}
}
