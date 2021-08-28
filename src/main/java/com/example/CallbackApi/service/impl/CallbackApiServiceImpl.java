package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import com.example.CallbackApi.gateway.ThirdPartyServiceGateway;
import com.example.CallbackApi.service.CallbackApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CallbackApiServiceImpl implements CallbackApiService {

    @Autowired
    ThirdPartyServiceGateway thirdPartyServiceGateway;

    @Override
    public String startCallback(final StartCallbackRequest startCallbackRequest) {
        final String callbackId = UUID.randomUUID().toString();

        thirdPartyServiceGateway.thirdPartyStartCallback(
            getThirdPartyCallbackRequest(startCallbackRequest.getBody(), callbackId)
        );

        return callbackId;
    }

    protected ThirdPartyCallbackRequest getThirdPartyCallbackRequest(final String body, final String callbackId) {
        ThirdPartyCallbackRequest thirdPartyCallbackRequest = new ThirdPartyCallbackRequest();
        thirdPartyCallbackRequest.setBody(body);
        thirdPartyCallbackRequest.setCallback("/callback/" + callbackId); //putting part of a uri in a request seems weird so I'd probably ask whoever made the spec about that

        return thirdPartyCallbackRequest;
    }
}
