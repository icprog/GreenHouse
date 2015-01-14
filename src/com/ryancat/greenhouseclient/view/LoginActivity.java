package com.ryancat.greenhouseclient.view;

import com.ryancat.greenhouseclient.R;
import com.ryancat.greenhouseclient.R.layout;
import com.ryancat.greenhouseclient.controller.TaskConstants;
import com.ryancat.greenhouseclient.databean.GatewayListDataBean;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/***
 * 显示界面的activity。
 * 本activity应当只有界面逻辑，不牵扯业务逻辑。
 * @author RyanHu
 * 
 */
public class LoginActivity extends BaseActivity 
{
	private Button register_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initViews()
	{
		showView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.login_activity, null);
		register_btn = (Button) showView.findViewById(R.id.login_register_btn);
		register_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.login_register_btn:
			Intent intent = new Intent(this,ScannerActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void progressLogic()
	{
		//进入后首先拿客户端数据换取cid
		controller.userRegister(getTaskHandler());
	}

	@Override
	protected void  setTaskHandler()
	{
		taskHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch (msg.what)
				{
				case TaskConstants.USER_REGISTER_TASK:
					//注册回调
					if(msg.arg1==TaskConstants.TASK_SUCCESS)
					{
						//任务执行成功，需要进行下一步登录
						controller.userLogin(getTaskHandler());
					}
					else if(msg.arg1 ==TaskConstants.TASK_FAILED)
					{
						//任务执行失败，报错
					}
					break;
				case TaskConstants.USER_LOGIN_TASK:
					//登录回调
					if(msg.arg1==TaskConstants.TASK_SUCCESS)
					{
						//任务执行成功，需要进行下一步获取网关列表
						controller.showGatewayList(getTaskHandler());
					}
					else if(msg.arg1 ==TaskConstants.TASK_FAILED)
					{
						//任务执行失败，报错
					}
					break;
				case TaskConstants.SHOW_GATEWAY_TASK:
					//获取网关列表回调
					if(msg.arg1==TaskConstants.TASK_SUCCESS)
					{
						if(!controller.isGatewayListEmpty(msg.obj))
						{
							//网关列表是空的,跳转添加网关界面
							Intent intent = new Intent(LoginActivity.this,ScannerActivity.class);
							startActivity(intent);
							Toast.makeText(mApp, "网关列表为空，请添加网关", Toast.LENGTH_LONG).show();
						}
					}
					else if(msg.arg1 ==TaskConstants.TASK_FAILED)
					{
						//任务执行失败，报错
					}
					break;
				default:
					break;
				}
			}
			
		};
	}
}
