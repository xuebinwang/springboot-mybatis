package com.example.demo.common.httpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HttpAPIService {
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    /**
     * 无参
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()==200){
            return new HttpResult(response.getStatusLine().getStatusCode(),
                  "UTF-8").toString();
        }
            return new HttpResult(response.getStatusLine().getStatusCode(),
                null).toString();
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        for (Map.Entry<String, Object> entry : params.entrySet()){
            uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

    /**
     * 带参数的post
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        if (params != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            //构造form表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);

            //吧表单放到post
            httpPost.setEntity(urlEncodedFormEntity);

        }
        //发起的请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        return new HttpResult(response.getStatusLine().getStatusCode(),
                EntityUtils.toString(response.getEntity(), "UTF-8"));
    }


    public  <R extends HttpResult> R test (String url, TypeReference<R> typeReference) throws Exception {
        if (typeReference == null) {
            typeReference = (TypeReference<R>) new TypeReference<HttpResult>() {
            };
        }
        String result = doGet(url);
        return JSON.parseObject(result, typeReference);
    }
}
