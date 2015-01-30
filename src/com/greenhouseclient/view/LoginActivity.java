package com.greenhouseclient.view;

import com.greenhouseclient.controller.TaskConstants;
import com.greenhouseclient.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/***
 * 显示界面的activity。 本activity应当只有界面逻辑，不牵扯业务逻辑。
 * 
 * @author RyanHu
 * 
 */
public class LoginActivity extends BaseActivity
{
	private Button gateway_add_btn;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initViews()
	{
		showView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.login_activity, null);
		gateway_add_btn = (Button) showView.findViewById(R.id.gateway_add_btn);
		gateway_add_btn.setOnClickListener(this);
		gateway_add_btn.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.gateway_add_btn:
			startActivityByName(ScannerActivity.class);
			LoginActivity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void progressLogic()
	{
		// 就一件事：进入后首先拿客户端数据换取cid
		controller.userRegister(getTaskHandler());
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setTitle("加载中");
		mProgressDialog.show();
	}

	@Override
	protected void setTaskHandler()
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
					// 注册回调
					if (msg.arg1 == TaskConstants.TASK_SUCCESS)
					{
						// 任务执行成功，需要进行下一步登录
						mProgressDialog.setMessage("认证成功正在进行登录！");
						controller.userLogin(getTaskHandler());
					} else if (msg.arg1 == TaskConstants.TASK_FAILED)
					{
						// 任务执行失败，报错
					}
					break;
				case TaskConstants.USER_LOGIN_TASK:
					// 登录回调
					if (msg.arg1 == TaskConstants.TASK_SUCCESS)
					{
						// 任务执行成功，需要进行下一步获取网关列表
						mProgressDialog.setMessage("登录成功正在更新网关列表！");
						controller.showGatewayList(getTaskHandler());

					} else if (msg.arg1 == TaskConstants.TASK_FAILED)
					{
						// 任务执行失败，报错
					}
					break;
				case TaskConstants.SHOW_GATEWAY_TASK:
					// 获取网关列表回调
					if (msg.arg1 == TaskConstants.TASK_SUCCESS)
					{
						if (!controller.isGatewayListEmpty(msg.obj))
						{
							gateway_add_btn.setVisibility(View.VISIBLE);
							mProgressDialog.setMessage("网关列表为空，请添加网关！");
							Toast.makeText(mApp, "网关列表为空，请添加网关", Toast.LENGTH_LONG).show();
						} else
						{
							// controller.delGateway(getTaskHandler(), 1);
							// controller.delGateway(getTaskHandler(), 1);
							// controller.delGateway(getTaskHandler(), 1);
							// controller.delGateway(getTaskHandler(), 1);
							gateway_add_btn.setVisibility(View.INVISIBLE);
							mProgressDialog.setMessage("网关列表不为空，欢迎使用！");
							startActivityByName(ShowGreenHouseActivity.class);
							LoginActivity.this.finish();
						}
						mProgressDialog.dismiss();
					} else if (msg.arg1 == TaskConstants.TASK_FAILED)
					{
						// 任务执行失败，报错
					}
					break;
				default:
					break;
				}
			}

		};
	}
}
