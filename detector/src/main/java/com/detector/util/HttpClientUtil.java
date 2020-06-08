package com.detector.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	public static boolean getBase1ReponseString() throws ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginname", "admin01"));
		params.add(new BasicNameValuePair("pwd", "Dtt!234567890"));
		HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).setConnectionRequestTimeout(5000).build();
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://10.173.20.43:10010/BASE/randd/ibizutil/login.do");
		post.setEntity(httpEntity);
		post.setConfig(requestConfig);
		
		HttpResponse response = httpClient.execute(post);
		if(response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			String message = EntityUtils.toString(resEntity, "utf-8"); 
			JSONObject jsonObj =  JSON.parseObject(message);
			if(jsonObj.get("isSa").toString().equals("1")) {
				return true;
			}
			return false;
		}
		
		else {
			System.out.println("«Î«Û ß∞‹");
		}
		
		
		return false;
	}
	
	
	public static boolean getBase2ResponseString() throws UnsupportedEncodingException {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("loginname", "admin01"));
		list.add(new BasicNameValuePair("pwd", "Dtt!234567890"));
		HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
		
		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
		HttpPost post = new HttpPost();
		post.setURI(URI.create("http://10.173.20.44:10010/BASE/randd/ibizutil/login.do"));
		
		
		return false;
	}
	public static void main(String args[]) throws ClientProtocolException, IOException {
		boolean meetError = getBase1ReponseString();
		
		
	}
}
