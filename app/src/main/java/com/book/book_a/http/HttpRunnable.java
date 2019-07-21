package com.book.book_a.http;


import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpRunnable implements Runnable {

    private static final int httpConnTimeOut = 120 * 1000;

    private static final int httpReadTimeOut = 120 * 1000;


    public static final String Get = "GET";

    public static final String Post = "POST";


    private Map<String, String> params = new HashMap<String, String>();

    private Map<String, String> header = new HashMap<String, String>();

    private String bodyjson;

    private String url;

    private String method;


    public interface CallBack {

        void success(String res);

        void onFailed(Exception e);
    }


    private CallBack callBack;


    public HttpRunnable(String method, String url, CallBack callBack) {
        this.method = method;
        this.url = url;
        this.callBack = callBack;
    }


    public HttpRunnable(String method, String url, Map<String, String> params, CallBack callBack) {
        this.params = params;
        this.url = url;
        this.method = method;
        this.callBack = callBack;
    }

    public HttpRunnable(String method, String url, Map<String, String> header,
                        Map<String, String> params, CallBack callBack) {
        this.header = header;
        this.url = url;
        this.params = params;
        this.method = method;
        this.callBack = callBack;
    }

    public HttpRunnable(String method, String url, Map<String, String> header,
                        Map<String, String> params, String bodyjson, CallBack callBack) {
        this.header = header;
        this.url = url;
        this.params = params;
        this.bodyjson = bodyjson;
        this.method = method;
        this.callBack = callBack;
    }

    private void addHeader(HttpURLConnection connection) {
        for (Map.Entry<String, String> next : header.entrySet()) {
            connection.setRequestProperty(next.getKey(), next.getValue());
        }
    }


    private void addBody(HttpURLConnection connection) throws Exception {

        if (TextUtils.isEmpty(bodyjson))
            return;

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        // 把数据写入请求的Body
        out.write(bodyjson);
        out.flush();
        out.close();
    }

    private void addParams() {
        url = url + "?ct=" + System.currentTimeMillis();
        for (Map.Entry<String, String> next : params.entrySet()) {
            String key = next.getKey();
            String value = next.getValue();
            url += "&" + key + "=" + value;
        }
    }


    @Override
    public void run() {
        addParams();

        String response = "";

        try {
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            TrustManager[] tm = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            sslcontext.init(null, tm, new SecureRandom());

            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslsession) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());


            URL u = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(httpConnTimeOut);
            connection.setReadTimeout(httpReadTimeOut);
            // 添加请求头
            if (header != null) {
                addHeader(connection);
            }

            // 添加请求body
            if (!TextUtils.isEmpty(bodyjson)) {
                addBody(connection);
            }


            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp = "";
                while ((temp = reader.readLine()) != null) {
                    response += temp;
                }
                reader.close();
            }
            connection.disconnect();
            callBack.success(response);
        } catch (Exception e) {
            callBack.onFailed(e);
        }
    }
}
