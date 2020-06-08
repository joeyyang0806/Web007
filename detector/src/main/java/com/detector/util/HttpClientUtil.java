package com.detector.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            JSONObject jsonObj = JSON.parseObject(message);
            if (jsonObj.get("isSa").toString().equals("1")) {
                return true;
            }
            return false;
        } else {
            System.out.println("发生错误。");
        }


        return false;
    }


    public static boolean getBase2ResponseString() throws Exception, IOException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("loginname", "admin01"));
        list.add(new BasicNameValuePair("pwd", "Dtt!234567890"));
        HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");

        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        HttpPost post = new HttpPost();
        post.setURI(URI.create("http://10.173.20.44:10010/BASE/randd/ibizutil/login.do"));
        post.setEntity(entity);
        post.setConfig(config);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity res = response.getEntity();
            String resStr = EntityUtils.toString(res, "UTF-8");
            JSONObject json = JSON.parseObject(resStr);
            if (json.getString("isSa").equals("1")) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        boolean meetError = getBase1ReponseString();
        meetError = getBase2ResponseString();
    }

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder clientBuilder = null;
    private static RequestConfig requestConfig = null;
    private static int maxConnection = 10;
    private static int defaultMaxConnection = 5;
    private static String ip = "http://172.16.150.162:8080/BASE/randd/ibizutil/login.do";
    private static int port = -1;

    static {
    	requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
		HttpHost host = new HttpHost(ip, port);
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxConnection);
		connectionManager.setDefaultMaxPerRoute(defaultMaxConnection);
		connectionManager.setMaxPerRoute(new HttpRoute(host), 20);
		clientBuilder = HttpClients.custom();
		clientBuilder.setConnectionManager(connectionManager);
	}

	public static CloseableHttpClient getConnection() {
        CloseableHttpClient client = clientBuilder.build();
        return client;
    }


}
