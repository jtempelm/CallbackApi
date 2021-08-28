package com.example.CallbackApi.gateway.impl;

import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import com.example.CallbackApi.gateway.ThirdPartyServiceGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ThirdPartyServiceGatewayImpl implements ThirdPartyServiceGateway {
    /* I would mock this host with a local proxy or a etc/hosts entry so tests are repeatable and don't spam
        an actual service unless in higher envs as needed */
    public static final String HOSTNAME = "http://example.com";

    @Override
    public String thirdPartyStartCallback(final ThirdPartyCallbackRequest thirdPartyCallbackRequest) {
//        return actuallyCallThirdPartyService(thirdPartyCallbackRequest);
        return "ok";
    }

    private String actuallyCallThirdPartyService(final ThirdPartyCallbackRequest thirdPartyCallbackRequest) {
        final String uri = "/request";
        final String queryParams = "";

        CloseableHttpClient httpclient = HttpClients.createDefault(); //in apache commons we trust (•◡•)
        HttpPost httpPost = new HttpPost(HOSTNAME + uri + queryParams);

        ObjectMapper objectMapper = new ObjectMapper(); //if I had more calls to make this would be a shared instance obviously
        String responseBody = null;
        CloseableHttpResponse postResponse;
        try {
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(thirdPartyCallbackRequest)));

            postResponse = httpclient.execute(httpPost);
            responseBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);

            postResponse.close();
        } catch (IOException e) {
            e.printStackTrace(); //obviously in an app server context a logger, slf4j or Log4j w/e would be used with log level
        }

        return responseBody;
    }

}
