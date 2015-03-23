package com.greenhouseclient.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.greenhouseclient.DataKeeper;
import com.greenhouseclient.GreenHouseApplication;
import com.greenhouseclient.databean.DataList;
import com.greenhouseclient.databean.DataRequestBean;
import com.greenhouseclient.databean.MQTT_DetectorDataBean;
import com.greenhouseclient.util.Constants;

import android.content.Context;
import android.os.Handler;

/**
 * 获取一天的数据
 * @author RyanHu
 *
 */
public class GetDataByHourTask extends BaseTask
{
	private static final int TASK_TAG = TaskConstants.GET_DATA_BY_HOUR_TASK;
	private long startTime ;
	private long endTime ;

	
	public GetDataByHourTask(Handler handler, Context _context)
	{
		super(handler, _context);
		endTime = new Date().getTime();
		startTime = endTime-Constants.DATE_TIME;

		
	}

	@Override
	public void run()
	{

		//循环网关数目个，去请求探头数据
		for(int i =0; i<GreenHouseApplication.gatawayListBean.gwList.length ;i++)
		{
			DataRequestBean dataRequestBean = new DataRequestBean();
			dataRequestBean.cid = GreenHouseApplication.cid;
			dataRequestBean.cToken = GreenHouseApplication.cToken;
			dataRequestBean.gwid = GreenHouseApplication.gatawayListBean.gwList[i].gwid;
			dataRequestBean.startTime  = startTime;
			dataRequestBean.endTime = endTime;
			dataRequestBean = (DataRequestBean) httpManager.requestServer(Constants.GetDataByHour_Url, dataRequestBean, true);
			//开始封装数据
			//先声明网关
			Map<Integer,ArrayList<MQTT_DetectorDataBean>> gwdataMap =DataKeeper.All_GW_Collections_Hour.get(GreenHouseApplication.gatawayListBean.gwList[i].gwid);
			if(gwdataMap == null)
			{
				//如果木有这个网关，就new一个
				 gwdataMap = new HashMap<Integer, ArrayList<MQTT_DetectorDataBean>>();
			}
			//遍历探头
			for(int j =0 ; j<dataRequestBean.dataList.length; j++){
				DataList t_DataList = dataRequestBean.dataList[j];
				for(int x = 0;x < t_DataList.detectorDatas.length;x++){
					MQTT_DetectorDataBean  newBean = new MQTT_DetectorDataBean();
					newBean.beam= t_DataList.detectorDatas[x].beam;
					newBean.humidity=t_DataList.detectorDatas[x].humidity;
					newBean.temperature=t_DataList.detectorDatas[x].temperature;
					newBean.warning=t_DataList.detectorDatas[x].waring;
					newBean.did =t_DataList.detectorDatas[x].did;
					newBean.message =t_DataList.detectorDatas[x].message;
//					if(newBean.temperature <-50)
//					{
//						newBean.temperature = 0;
//					}
//					if(newBean.humidity<-50)
//					{
//						newBean.humidity = 0;
//					}
//					if(newBean.beam<-50)
//					{
//						newBean.beam = 0;
//					}
//
					ArrayList<MQTT_DetectorDataBean> detectorList = gwdataMap.get(t_DataList.detectorDatas[x].did);
					if(detectorList ==null)
					{
						//如果探头的Arraylist是空，再new一个
						detectorList =  new ArrayList<MQTT_DetectorDataBean>();
					}
					
					detectorList.add(newBean);
					gwdataMap.put(t_DataList.detectorDatas[x].did, detectorList);
				}
				//声明探头 
//				ArrayList<MQTT_DetectorDataBean> detectorList = gwdataMap.get(dataRequestBean.dataList[0].detectorDatas[0].did);
//				if(detectorList ==null)
//				{
//					//如果探头的Arraylist是空，再new一个
//					detectorList =  new ArrayList<MQTT_DetectorDataBean>();
//				}
//				MQTT_DetectorDataBean newBean = new MQTT_DetectorDataBean() ;
//				newBean.did = dataRequestBean.dataList[0].detectorDatas[0].did;
//				newBean.name = dataRequestBean.dataList[0].detectorDatas[0].name;
//				newBean.temperature = dataRequestBean.dataList[0].detectorDatas[0].temperature;
//				newBean.humidity = dataRequestBean.dataList[0].detectorDatas[0].humidity;
//				newBean.beam = dataRequestBean.dataList[0].detectorDatas[0].beam;
//				newBean.message = dataRequestBean.dataList[0].detectorDatas[0].message;
//				newBean.warning =dataRequestBean.dataList[0].detectorDatas[0].waring;
//				detectorList.add(newBean);
//				gwdataMap.put(dataRequestBean.dataList[0].detectorDatas[0].did, detectorList);
			}
			DataKeeper.All_GW_Collections_Hour.put(GreenHouseApplication.gatawayListBean.gwList[i].gwid, gwdataMap);
		}
		//循环结束，数据获取完毕，发送消息通知上层
		sendResultMessage(TASK_TAG, null, TaskConstants.TASK_SUCCESS, 0);
	}

}
