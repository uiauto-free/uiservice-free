package com.uiautofree.common.utils;

import com.uiautofree.common.domain.HttpCommonResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static com.uiautofree.common.constant.CommonConstant.Agent.TOKEN;

@Slf4j
public class HttpClientUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                                                                         .readTimeout(20, TimeUnit.SECONDS).build();

    public static HttpCommonResult doGetReturnHttpResult(String url) throws IOException {
        HttpCommonResult result = new HttpCommonResult();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        result.setHttpResponseCode(response.code());
        result.setResult(response.body().string());
        return result;
    }

    public static HttpCommonResult doGetReturnHttpResultWithHeaders(String url, Headers headers) throws IOException {
        HttpCommonResult result = new HttpCommonResult();
        Request request = new Request.Builder().url(url).headers(headers).build();
        Response response = okHttpClient.newCall(request).execute();
        result.setHttpResponseCode(response.code());
        result.setResult(response.body().string());
        return result;
    }

    public static HttpCommonResult doPostReturnHttpResult(String url, String json) throws IOException {
        log.info("[doPostReturnHttpResult] url={}, json={}", url, json);
        HttpCommonResult result = new HttpCommonResult();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("token", TOKEN)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        result.setHttpResponseCode(response.code());
        result.setResult(response.body().string());
        log.info("[doPostReturnHttpResult] result : {}", result);
        return result;
    }

    public static InputStream getInputStream(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("token", TOKEN)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().byteStream();
    }

}
