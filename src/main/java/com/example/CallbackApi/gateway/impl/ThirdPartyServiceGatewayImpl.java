package com.example.CallbackApi.gateway.impl;

import com.example.CallbackApi.gateway.ThirdPartyServiceGateway;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ThirdPartyServiceGatewayImpl implements ThirdPartyServiceGateway {
    public static final String HOSTNAME = "http://example.com";

    @Override
    public String thirdPartyStartCallback(final String callbackId) {
        final String uri = "/request";
        final String queryParams = "";

        CloseableHttpClient httpclient = HttpClients.createDefault(); //in apache commons we trust (•◡•)

        HttpPost httpPost = new HttpPost(HOSTNAME + uri + queryParams);
        String encodedJson = "{ \"wouldIUseAClassForThis\": \"yes\" }";
        try {
            httpPost.setEntity(new StringEntity(encodedJson));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CloseableHttpResponse postResponse = httpclient.execute(httpPost);

        try {
            postResponse = httpclient.execute(httpPost);
            Header[] headers = postResponse.getAllHeaders();
            String responseBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postResponse.close();
        }

        return null;
    }
}
