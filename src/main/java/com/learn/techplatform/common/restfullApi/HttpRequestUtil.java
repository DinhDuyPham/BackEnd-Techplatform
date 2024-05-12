package com.learn.techplatform.common.restfullApi;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.nio.charset.StandardCharsets;
import com.jayway.jsonpath.JsonPath;

@Slf4j
@Getter
@Component
public class HttpRequestUtil {
    public HttpResponse getRequest(String url, ContentType contentType, Map<String, String> headers)
            throws IOException {

        HttpGet request = new HttpGet(url);

        if (contentType != null) {
            request.addHeader("Content-Type", contentType.getMimeType());
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        log.info(request.getRequestLine().toString());
        HttpClient client =
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(
                                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                        .build();
        return client.execute(request);
    }

    public HttpResponse postRequest(
            HttpEntity entity, String url, ContentType contentType, Map<String, String> headers)
            throws IOException {

        HttpPost request = new HttpPost(url);
        if (contentType != null) {
            request.addHeader("Content-Type", contentType.getMimeType());
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        request.setEntity(entity);
        log.info(request.getRequestLine().toString());
        HttpClient client =
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(
                                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                        .build();
        return client.execute(request);
    }

    public HttpResponse putRequest(HttpEntity entity, String url, ContentType contentType)
            throws IOException {

        HttpPut request = new HttpPut(url);
        if (contentType != null) {
            request.addHeader("Content-Type", contentType.getMimeType());
        }
        request.setEntity(entity);
        log.info(request.getRequestLine().toString());
        HttpClient client =
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(
                                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                        .build();
        return client.execute(request);
    }

    public HttpResponse deleteRequest(String url) throws IOException {

        HttpDelete request = new HttpDelete(url);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        log.info(request.getRequestLine().toString());
        HttpClient client =
                HttpClientBuilder.create()
                        .setDefaultRequestConfig(
                                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                        .build();
        return client.execute(request);
    }

    public String queryProvider(List<NameValuePair> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder();
        AtomicBoolean first = new AtomicBoolean(true);
        params.forEach(
                basicNameValuePair -> {
                    if (first.get()) {
                        query.append("?");
                        query.append(basicNameValuePair.toString().replaceAll(" ", "+"));
                        first.set(false);
                    } else {
                        query.append("&");
                        query.append(basicNameValuePair.toString().replaceAll(" ", "+"));
                    }
                });
        return query.toString();
    }

    public HttpEntity bodyProvider(List<NameValuePair> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        return new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
    }

    public <T> T readData(String responseBody, String jsonPath, T defaultData, boolean required) {
        try {
            return JsonPath.read(responseBody, jsonPath);
        } catch (Exception e) {
            if (required) {
                throw e;
            } else {
                return defaultData;
            }
        }
    }
}
