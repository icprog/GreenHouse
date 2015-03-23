package com.greenhouseclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.greenhouseclient.databean.MQTT_DetectorDataBean;
import com.greenhouseclient.databean.GatewayBean;
import com.greenhouseclient.util.Constants;

/**
 * 保存数据的一个dataKeeper
 * 
 * @author RyanHu
 * 
 */
public class DataKeeper
{
	/**存放<网关ID，网关<探头ID，List<探头数据>>>探头数据*/
	public static Map<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>> All_GW_Collections_Minute = new HashMap<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>>();
	public static Map<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>> All_GW_Collections_Hour = new HashMap<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>>();
	public static Map<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>> All_GW_Collections_Date = new HashMap<Integer,Map<Integer,ArrayList<MQTT_DetectorDataBean>>>();
}
