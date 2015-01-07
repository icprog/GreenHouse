package com.ryancat.greenhouseclient.network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.ryancat.greenhouseclient.util.Constants;
import com.ryancat.greenhouseclient.util.L;


import android.content.Context;

/**
 * 
 * @author wangyang HttpClient的工具类,主要封装了以Post请求和HttpClient向服务器发送请求,并得到返回结果的类。
 */
public class HttpAdapter
{
	private static final String TAG = "HttpClientAdapter";
	/*
	 * 定义了成员变量HttpClient,HttpPost,HttpRequest,HttpResponse
	 */
	HttpClient mHttpClient;
	static HttpPost mHttpPost;
	HttpResponse mResponse;
	HttpGet mHttpGet;
	BasicHttpParams mHttparams;

	private static final int REQUEST_TIMEOUT = 10 * 1000;// 设置请求超时10秒钟
	private static final int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟

	/**
	 * 
	 * @author wangyang 该工具类的构造方法,初始化了HttpClient
	 */
	public HttpAdapter(Context context)
	{
		super();
		mHttparams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(mHttparams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(mHttparams, SO_TIMEOUT);
		this.mHttpClient = new DefaultHttpClient(mHttparams);
	}

	public String get(String url, Map<String, Object> parameters)
	{
		// Log.e(TAG, "get parameters" + parameters);
		mHttpGet = new HttpGet(url);

		// 如果参数集合不为空,设置请求参数
		try
		{
			if (parameters != null)
				// setParameters(parameters, mHttpGet);
				setGetParameters(parameters, mHttpGet);
		}
		catch (Exception e)
		{
			L.e("参数设置,不支持的编码格式");
		}

		// 发送请求并得到mResponse对象
		try
		{
			// Log.e(TAG, "mHttpGet url is ::" + mHttpGet.getURI());
			//
			// Log.e(TAG,
			// "mHttpGet params is :" + "macAddress:"
			// + mHttpGet.getParams().getParameter("macAddress")
			// + "&&softwareVer:"
			// + mHttpGet.getParams().getParameter("softwareVer")
			// + "&&protocalVer :"
			// + mHttpGet.getParams().getParameter("protocolVer"));
			//
			mResponse = mHttpClient.execute(mHttpGet);
			// Log.e(TAG, "get response:" + mResponse.toString());
		}
		catch (ClientProtocolException e)
		{
			L.e("客户端提交给服务器的请求，不符合HTTP协议。");

		}
		catch (IllegalArgumentException e)
		{
			L.e("请求参数错误!");

		}
		catch (ConnectTimeoutException e)
		{
			L.e("请求服务器超时!");

		}
		catch (HttpHostConnectException e)
		{
			L.e("请求地址错误!");

		} 
		catch (IOException e)
		{
			L.e("请求服务器出错");

		}

		// 得到返回码并根据返回码的状态做相应的处理操作
		int returnCode = mResponse.getStatusLine().getStatusCode();
		try
		{
			switch (returnCode)
			{
			case 200:
				return EntityUtils.toString(mResponse.getEntity(), Constants.ENCODING);
			case 302:
				return EntityUtils.toString(mResponse.getEntity(), Constants.ENCODING);
			case 404:
				L.e("404 NOT FOUND");
			case 500:
				L.e("服务器出错!");
				break;
			}
		}
		catch (ParseException e)
		{
			L.e("获取返回结果出错");

		}
		catch (IOException e)
		{
			L.e("获取返回结果出错");
		}

		return "";
	}

	/**
	 * 
	 * @author wangyang
	 * @param url
	 * @param parameters
	 * @return 根据url地址及请求参数向服务器发送请求并获取返回结果
	 */
	public String post(String url, Map<String, Object> parameters)
	{
		L.d( "post request url is:" + url);
		L.d("post parameters" + parameters);
		// 根据url初始化HttpPost
		mHttpPost = new HttpPost(url);

		// 如果参数集合不为空,设置请求参数
		try
		{
			if (parameters != null)
				setParameters(parameters, mHttpPost);
		}
		catch (UnsupportedEncodingException e)
		{
			L.e("参数设置,不支持的编码格式");
		}

		// 发送请求并得到mResponse对象
		try
		{
			mResponse = mHttpClient.execute(mHttpPost);
		}
		catch (IllegalArgumentException e)
		{
			L.e("请求地址错误!");
		}
		catch (ConnectTimeoutException e)
		{
			L.e("ConnectTimeoutException");
		}
		catch (SocketTimeoutException e)
		{
			L.e("SocketTimeoutException");
		}
		catch (ClientProtocolException e)
		{
			L.e("客户端提交给服务器的请求，不符合HTTP协议。");
		}
		catch (HttpHostConnectException e)
		{
			L.e("HttpHostConnectException");

		}
		catch (Exception e)
	
		{
			
			L.e("请求服务器出错");

		}

		try
		{
			// 得到返回码并根据返回码的状态做相应的处理操作
			if (mResponse == null)
			{
				L.e( "HttpClientAdapter  mResponse  = null~");
				return "";
			}
			int returnCode = mResponse.getStatusLine().getStatusCode();
			L.d( "returnCode :" + returnCode);
			switch (returnCode)
			{
			case 200:
				return  EntityUtils.toString(mResponse.getEntity(),Constants.ENCODING);
			case 302:
				return EntityUtils.toString(mResponse.getEntity(), Constants.ENCODING);
			case 404:
				L.e( "404 NOT FOUND");
			case 500:
				L.e( "服务器出错！");
				break;
			}
		}
		catch (ParseException e)
		{
			L.e( "获取返回结果出错");
		}
		catch (IOException e)
		{
			L.e( "获取返回结果出错");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * @author wangyang
	 * @2013-10-22 下午4:29:19
	 * @param parameters
	 * @param mHttpGet
	 * @throws UnsupportedEncodingException
	 *             将Map参数集合设置到HttpGet中
	 */
	@SuppressWarnings("unused")
	private void setParameters(Map<String, String> parameters, HttpGet mHttpGet) throws UnsupportedEncodingException
	{
		HttpParams params = new BasicHttpParams();

		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			params.setParameter(entry.getKey(), entry.getValue());
		}
		mHttpGet.setParams(params);
	}

	private void setGetParameters(Map<String, Object> params, HttpGet get)
	{
		HttpParams httpParams = new BasicHttpParams();
		Set<String> keys = params.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			httpParams.setParameter(key, params.get(key));
		}
		get.setParams(httpParams);
	}

	/**
	 * 
	 * @author wangyang
	 * @param parameters
	 * @param post
	 * @throws UnsupportedEncodingException
	 *             将Map参数集合设置到HttpPost中
	 */
	private void setParameters(Map<String, Object> parameters, HttpPost post) throws UnsupportedEncodingException
	{
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		for (Map.Entry<String, Object> entry : parameters.entrySet())
		{
			list.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
		}

		post.setEntity(new UrlEncodedFormEntity(list, Constants.ENCODING));
	}
}
