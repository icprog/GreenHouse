package com.greenhouseclient.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.limc.androidcharts.entity.LineEntity;
import cn.limc.androidcharts.view.ITouchEventResponse;
import cn.limc.androidcharts.view.MAChart;

import com.greenhouseclient.DataKeeper;
import com.greenhouseclient.GreenHouseApplication;
import com.greenhouseclient.MQTTService;
import com.greenhouseclient.databean.MQTT_DetectorDataBean;
import com.greenhouseclient.databean.GatewayBean;
import com.greenhouseclient.util.L;
import com.greenhouseclient.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示大棚的核心Activity 这个类应当大量用到Android Chart
 * 
 * @author RyanHu
 * 
 */
public class ShowGatewayActivity extends BaseActivity
{
	private ViewPager mViewPager;
	private MyViewPagerAdapter mPagerAdapter;
	private static final int TYPE_HOUR = 1;
	private static final int TYPE_DAY = 2;
	private static final int TYPE_WEEK = 3;

	private static final int TEMP = -1;
	private static final int HUMI = -2;
	private static final int BEAM = -3;

	// TODO
	private static final int DEFAULT_DECETOR = 1;

	@Override
	protected void initViews()
	{
		// int gwid = getIntent().getIntExtra("gwid", 0);

		showView = (ViewGroup) getLayoutInflater().inflate(R.layout.gateway_center_activity, null);
		mMenuImageButton.setOnClickListener(this);
		mViewPager = (ViewPager) showView.findViewById(R.id.gateway_center_pager);

		// 初始化多界面
		if (GreenHouseApplication.gatawayListBean.gwList.length != 0)
		{
			mPagerAdapter = new MyViewPagerAdapter(new ArrayList<View>());
			for (int i = 0; i < GreenHouseApplication.gatawayListBean.gwList.length; i++)
			{
				View itemView = initPagerItemView(GreenHouseApplication.gatawayListBean.gwList[i].gwid);
				// itemView.findViewById(R.id.select_showdata_type).setTag(gwid);
				mPagerAdapter.addItemView(itemView);
			}
			mViewPager.setAdapter(mPagerAdapter);
		}
	}

	/**
	 * 初始化ViewPager的Item
	 * 
	 * @return
	 */
	private View initPagerItemView(int gwid)
	{
		View childView = getLayoutInflater().inflate(R.layout.gateway_page, null);
		childView.setTag(gwid);
		TextView gwNameTv = (TextView) childView.findViewById(R.id.gateway_page_title);
		gwNameTv.setText("网关id：" + gwid);

		View detectorView1 = (View) childView.findViewById(R.id.rl_ll_ll_ver1);
		View detectorView2 = (View) childView.findViewById(R.id.rl_ll_ll_ver2);
		View detectorView3 = (View) childView.findViewById(R.id.rl_ll_ll_ver3);
		View detectorView4 = (View) childView.findViewById(R.id.rl_ll_ll_ver4);
		detectorView1.setOnClickListener(this);
		detectorView2.setOnClickListener(this);
		detectorView3.setOnClickListener(this);
		detectorView4.setOnClickListener(this);
		detectorView1.setTag(gwid);
		// gwNameTv.setText("网关ID：" + gwid);
		//
		// MAChart temp_machart = (MAChart)
		// childView.findViewById(R.id.temp_machart);
		// MAChart humi_machart = (MAChart)
		// childView.findViewById(R.id.humi_machart);
		// MAChart beam_machart = (MAChart)
		// childView.findViewById(R.id.beam_machart);
		// initMACharts(temp_machart, TYPE_HOUR, TEMP, gwid);
		// initMACharts(humi_machart, TYPE_HOUR, HUMI, gwid);
		// initMACharts(beam_machart, TYPE_HOUR, BEAM, gwid);
		initDataText(childView, gwid);
		// Spinner sp = (Spinner)
		// childView.findViewById(R.id.select_showdata_type);
		// sp.setAdapter(new MySpinnerAdapter());
		// sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		// {
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view, int
		// position, long id)
		// {
		//
		// switch (position)
		// {
		// case 0:
		// {
		// View ViewPagerItem = (View) parent.getParent();
		// int gwid = (Integer) parent.getTag();
		// MAChart temp_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.temp_machart);
		// MAChart humi_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.humi_machart);
		// MAChart beam_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.beam_machart);
		// initMACharts(temp_machart, TYPE_HOUR, TEMP, gwid);
		// initMACharts(humi_machart, TYPE_HOUR, HUMI, gwid);
		// initMACharts(beam_machart, TYPE_HOUR, BEAM, gwid);
		// temp_machart.invalidate();
		// humi_machart.invalidate();
		// beam_machart.invalidate();
		// }
		//
		// break;
		// case 1:
		// {
		// View ViewPagerItem = (View) parent.getParent();
		// int gwid = (Integer) parent.getTag();
		// MAChart temp_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.temp_machart);
		// MAChart humi_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.humi_machart);
		// MAChart beam_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.beam_machart);
		// initMACharts(temp_machart, TYPE_DAY, TEMP, gwid);
		// initMACharts(humi_machart, TYPE_DAY, HUMI, gwid);
		// initMACharts(beam_machart, TYPE_DAY, BEAM, gwid);
		// temp_machart.invalidate();
		// humi_machart.invalidate();
		// beam_machart.invalidate();
		//
		// }
		// break;
		// case 2:
		// {
		// View ViewPagerItem = (View) parent.getParent();
		// int gwid = (Integer) parent.getTag();
		// MAChart temp_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.temp_machart);
		// MAChart humi_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.humi_machart);
		// MAChart beam_machart = (MAChart)
		// ViewPagerItem.findViewById(R.id.beam_machart);
		// initMACharts(temp_machart, TYPE_WEEK, TEMP, gwid);
		// initMACharts(humi_machart, TYPE_WEEK, HUMI, gwid);
		// initMACharts(beam_machart, TYPE_WEEK, BEAM, gwid);
		// temp_machart.invalidate();
		// humi_machart.invalidate();
		// beam_machart.invalidate();
		// }
		// break;
		//
		// default:
		// break;
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent)
		// {
		//
		// }
		// });

		return childView;
	}

	private void initDataText(View contentView, int gwid)
	{
		// TextView detName_tv = (TextView)
		// contentView.findViewById(R.id.rl_ll_ll_tv_decetorname1);

		TextView temp_tv = (TextView) contentView.findViewById(R.id.rl_ll_ll_tv_temp1);
		TextView humi_tv = (TextView) contentView.findViewById(R.id.rl_ll_ll_tv_humi1);
		TextView beam_tv = (TextView) contentView.findViewById(R.id.rl_ll_ll_tv_beam1);
		// detName_tv.setText("探测器:"+"1");
		if (DataKeeper.All_GW_Collections_Minute.get(gwid) != null)
		{
			List<MQTT_DetectorDataBean> list = DataKeeper.All_GW_Collections_Minute.get(gwid).get(DEFAULT_DECETOR);
			if (list != null && list.size() > 0)
			{
				// MQTT_DetectorDataBean currentData = list.get(list.size() -
				// 1);
				// temp_tv.setText(String.valueOf(currentData.temperature));
				// humi_tv.setText(String.valueOf(currentData.humidity));
				// beam_tv.setText(String.valueOf(currentData.beam));
				//
				for (int i = list.size() - 1; i > 0; i--)
				{
					MQTT_DetectorDataBean currentBean = list.get(i);

					if (currentBean.temperature > -50 || currentBean.humidity > -50 || currentBean.beam > -50)
					{
						temp_tv.setText(String.valueOf(currentBean.temperature));
						humi_tv.setText(String.valueOf(currentBean.humidity));
						beam_tv.setText(String.valueOf(currentBean.beam));
						return;
					}
				}
				temp_tv.setText(String.valueOf(0));
				humi_tv.setText(String.valueOf(0));
				beam_tv.setText(String.valueOf(0));
			}
		}
	}

	@Override
	protected void progressLogic()
	{
		controller.startMqttClient(mMQTTHandler);
		mMQTTHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				L.d("hand mqqtdata and begin initMAChart!");
				super.handleMessage(msg);
				int gwid = msg.arg1;
				try
				{

					View childView = mViewPager.findViewWithTag(gwid);
					L.d("find View with tag && tag == gwid ==" + gwid + ",childView == " + childView.toString());
					// initMACharts((MAChart)
					// childView.findViewById(R.id.temp_machart), TYPE_HOUR,
					// TEMP, gwid);
					// initMACharts((MAChart)
					// childView.findViewById(R.id.humi_machart), TYPE_HOUR,
					// HUMI, gwid);
					// initMACharts((MAChart)
					// childView.findViewById(R.id.beam_machart), TYPE_HOUR,
					// BEAM, gwid);
					// childView.findViewById(R.id.temp_machart).invalidate();
					// childView.findViewById(R.id.humi_machart).invalidate();
					// childView.findViewById(R.id.beam_machart).invalidate();
					initDataText(childView, gwid);
				} catch (Exception e)
				{
				}
			}
		};
		controller.startMqttClient(mMQTTHandler);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.title_menu_imgbtn:
			startActivityByName(ScannerActivity.class);
			// 点击右侧按钮，应该弹出
			break;
		case R.id.rl_ll_ll_ver1:
			int gwid = (Integer) v.getTag();
			Intent i = new Intent(this, ShowDetectorActivity.class);
			i.putExtra("gwid", gwid);
			startActivity(i);
			break;
		case R.id.rl_ll_ll_ver2:
			Toast.makeText(this, "尚未添加该探头！", 1).show();
			break;
		case R.id.rl_ll_ll_ver3:
			Toast.makeText(this, "尚未添加该探头！", 1).show();
			break;
		case R.id.rl_ll_ll_ver4:
			Toast.makeText(this, "尚未添加该探头！", 1).show();
			break;

		default:
			break;
		}
	}

	@Override
	protected void setTaskHandler()
	{

	}

	/**
	 * ViewPager适配器
	 * 
	 * @author RyanHu
	 * 
	 */
	private class MyViewPagerAdapter extends PagerAdapter
	{

		private List<View> Views;

		public MyViewPagerAdapter(List<View> _Views)
		{
			super();
			this.Views = _Views;
		}

		@Override
		public int getCount()
		{
			return Views.size();
		}

		public void addItemView(View _itemView)
		{
			Views.add(_itemView);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1)
		{
			((ViewPager) arg0).addView(Views.get(arg1), 0);
			return Views.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			((ViewPager) arg0).removeView(Views.get(arg1));
		}

	}

	// /**
	// * Spinner适配器
	// *
	// * @author RyanHu
	// *
	// */
	// private class MySpinnerAdapter extends BaseAdapter
	// {
	// private String[] mSpinnerContent = new String[]
	// { "实时数据", "一天数据", "一周数据" };
	//
	// @Override
	// public int getCount()
	// {
	// return mSpinnerContent.length;
	// }
	//
	// @Override
	// public Object getItem(int position)
	// {
	// return mSpinnerContent[position];
	// }
	//
	// @Override
	// public long getItemId(int position)
	// {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent)
	// {
	// View content_view = getLayoutInflater().inflate(R.layout.spinner_item,
	// null);
	// TextView tv = (TextView) content_view.findViewById(R.id.spinner_item_tv);
	// tv.setText(mSpinnerContent[position]);
	// return content_view;
	// }
	// }
	//
	static Handler mMQTTHandler;

	public static Handler getHandler()
	{
		return mMQTTHandler;
	}

	// public void initMACharts(MAChart machart, int TIME_TYPE, int dataTYPE,
	// int gwid)
	// {
	// List<String> ytitle = new ArrayList<String>();
	// ytitle.add("0");
	// ytitle.add("25");
	// ytitle.add("50");
	// ytitle.add("75");
	// ytitle.add("100");
	// List<String> xtitle = new ArrayList<String>();
	//
	// List<DetectorDataBean> listDataBean = null;
	// switch (TIME_TYPE)
	// {
	// case TYPE_HOUR:
	// xtitle.add("0分");
	// xtitle.add("5分");
	// xtitle.add("10分");
	// xtitle.add("15分");
	// xtitle.add("20分");
	// xtitle.add("25分");
	// xtitle.add("30分");
	// xtitle.add("35分");
	// xtitle.add("40分");
	// xtitle.add("45分");
	// xtitle.add("50分");
	// xtitle.add("55分");
	// xtitle.add("60分");
	// machart.setMaxPointNum(60);
	// if (DataKeeper.All_GW_Collections_Minute.get(gwid) != null)
	// listDataBean =
	// DataKeeper.All_GW_Collections_Minute.get(gwid).get(DEFAULT_DECETOR);
	// break;
	// case TYPE_DAY:
	// xtitle.add("0时");
	// xtitle.add("1");
	// xtitle.add("2");
	// xtitle.add("3");
	// xtitle.add("4");
	// xtitle.add("5");
	// xtitle.add("6");
	// xtitle.add("7");
	// xtitle.add("8");
	// xtitle.add("9");
	// xtitle.add("10");
	// xtitle.add("11");
	// xtitle.add("12");
	// xtitle.add("13");
	// xtitle.add("14");
	// xtitle.add("15");
	// xtitle.add("16");
	// xtitle.add("17");
	// xtitle.add("18");
	// xtitle.add("19");
	// xtitle.add("20");
	// xtitle.add("21");
	// xtitle.add("22");
	// xtitle.add("23");
	// xtitle.add("24");
	// machart.setMaxPointNum(24);
	// if (DataKeeper.All_GW_Collections_Hour.get(gwid) != null)
	//
	// listDataBean =
	// DataKeeper.All_GW_Collections_Hour.get(gwid).get(DEFAULT_DECETOR);
	// break;
	// case TYPE_WEEK:
	// xtitle.add("周一");
	// xtitle.add("周二");
	// xtitle.add("周三");
	// xtitle.add("周四");
	// xtitle.add("周五");
	// xtitle.add("周六");
	// xtitle.add("周日");
	// machart.setMaxPointNum(7);
	// if (DataKeeper.All_GW_Collections_Date.get(gwid) != null)
	//
	// listDataBean =
	// DataKeeper.All_GW_Collections_Date.get(gwid).get(DEFAULT_DECETOR);
	// break;
	// default:
	// break;
	// }
	// List<Float> data = new ArrayList<Float>();
	// if (listDataBean != null)
	// {
	//
	// switch (dataTYPE)
	// {
	// case TEMP:
	// {
	// Iterator<DetectorDataBean> iterator = listDataBean.iterator();
	// while (iterator.hasNext())
	// {
	// DetectorDataBean greenHouseBaseDataBean = (DetectorDataBean)
	// iterator.next();
	// data.add((float) greenHouseBaseDataBean.temperature);
	// }
	// machart.setMaxValue(100);
	// }
	// break;
	//
	// case HUMI:
	// {
	// Iterator<DetectorDataBean> iterator = listDataBean.iterator();
	// while (iterator.hasNext())
	// {
	// DetectorDataBean greenHouseBaseDataBean = (DetectorDataBean)
	// iterator.next();
	// data.add((float) greenHouseBaseDataBean.humidity);
	// }
	// machart.setMaxValue(100);
	// }
	// break;
	//
	// case BEAM:
	// {
	// Iterator<DetectorDataBean> iterator = listDataBean.iterator();
	// while (iterator.hasNext())
	// {
	// DetectorDataBean greenHouseBaseDataBean = (DetectorDataBean)
	// iterator.next();
	// data.add((float) greenHouseBaseDataBean.beam);
	// }
	// machart.setMaxValue(300);
	// }
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	// List<LineEntity> lines = new ArrayList<LineEntity>();
	//
	// LineEntity MA = new LineEntity();
	// MA.setTitle("Title");
	// MA.setLineColor(Color.BLACK);
	// MA.setLineData(data);
	// lines.add(MA);
	// machart.setBackgroudColor(Color.WHITE);
	// machart.setDisplayCrossXOnTouch(false);
	// machart.setAxisXColor(Color.BLACK);
	// machart.setAxisYColor(Color.BLACK);
	// machart.setBorderColor(Color.BLACK);
	// machart.setAxisMarginTop(10);
	// machart.setAxisMarginLeft(20);
	// machart.setAxisYTitles(ytitle);
	// machart.setAxisXTitles(xtitle);
	// machart.setLongtitudeFontSize(20);
	// machart.setLongtitudeFontColor(Color.BLACK);
	// machart.setLatitudeColor(Color.BLACK);
	// machart.setLatitudeFontColor(Color.BLACK);
	// machart.setLongitudeColor(Color.BLACK);
	// machart.setMinValue(0);
	// machart.setLatitudeFontSize(20);
	// machart.setLongtitudeFontSize(10);
	// machart.setDisplayAxisXTitle(true);
	// machart.setDisplayAxisYTitle(true);
	// machart.setDisplayLatitude(true);
	// machart.setDisplayLongitude(true);
	// // 为chart1增加均线
	// machart.setLineData(lines);
	// // machart.addNotify(new ITouchEventResponse()
	// }
}
