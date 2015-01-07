package com.ryancat.greenhouseclient.network;

import java.util.Map;

public class HttpManager
{
	private HttpAdapter adapter;
	private static  HttpManager sInstance = new HttpManager();

	private HttpManager()
	{
	}

	public static HttpManager getInstance()
	{
		return sInstance;
	}

	public String httpGet(String url, Map<String, Object> param)
	{
		return adapter.get(url, param);
	}

	public String httpPost(String url, Map<String, Object> param)
	{
		return adapter.post(url, param);
	}
}
