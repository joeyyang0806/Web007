package com.detector.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public static boolean getBase3ResponseString() throws UnsupportedEncodingException, IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginname", "admin01"));
        params.add(new BasicNameValuePair("pwd", "Dtt!234567890"));
        HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost();
        post.setURI(URI.create("http://10.173.20.43:10010/BASE/rannd/ibizutil/login.do"));
        post.setConfig(requestConfig);
        post.setEntity(entity);

        try {
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jObj = JSON.parseObject(resStr);

                if (jObj.getString("isSa").equals("1")) {
                    return true;
                }

                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }



    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder clientBuilder = null;
    private static RequestConfig requestConfig = null;
    private static int maxConnection = 10;
    private static int defaultMaxConnection = 5;
    private static String ip = "10.173.20.43";
    private static int port = 10010;

    static {
        PlainConnectionSocketFactory sf = PlainConnectionSocketFactory.getSocketFactory();
        SSLConnectionSocketFactory sslf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", sf).register("https", sslf).build();
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
        HttpHost host = new HttpHost(ip, port);
        connectionManager = new PoolingHttpClientConnectionManager(registry);
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

    public static boolean getBase4ResponseStr() throws IOException {
        HttpClient client = getConnection();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("loginname", "admin01"));
        params.add(new BasicNameValuePair("pwd", "Dtt!234567890"));
        HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        HttpPost post = new HttpPost("http://10.173.20.43:10010/BASE/randd/ibizutil/login.do");
        post.setEntity(entity);
        post.setConfig(requestConfig);

        HttpResponse response = client.execute(post, HttpClientContext.create());
        if(response.getStatusLine().getStatusCode()==200) {
            JSONObject jobj = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            if(jobj.getString("isSa").equals("1")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean getBase5ResponseStr() throws IOException {
        HttpClient client = getConnection();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("loginname", "admin01"));
        params.add(new BasicNameValuePair("pwd", "Dtt!234567890"));

        HttpUriRequest uriRequest = RequestBuilder.post().setUri("http://10.173.20.43:10010/BASE/randd/ibizutil/login.do")
                .addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig).build();
        HttpResponse response = client.execute(uriRequest);
        if(response.getStatusLine().getStatusCode()==200) {
            HttpEntity entity = response.getEntity();
            JSONObject jobj = JSON.parseObject(EntityUtils.toString(entity, "utf-8"));
            if(jobj.getString("isSa").equals("1")) {
                return true;
            }
            else {
                return false;
            }

        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        boolean meetError = getBase1ReponseString();
        System.out.println(meetError);
        meetError = getBase2ResponseString();
        System.out.println(meetError);
        meetError = getBase4ResponseStr();
        System.out.println(meetError);
        meetError = getBase5ResponseStr();
        System.out.println(meetError);
    }
}
