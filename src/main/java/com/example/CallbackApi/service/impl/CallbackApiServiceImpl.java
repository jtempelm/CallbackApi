package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.StartCallbackRequest;
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



        return callbackId;
    }
}
