package com.example.CallbackApi.gateway;

import org.springframework.stereotype.Service;

@Service
public interface ThirdPartyServiceGateway {

    String thirdPartyStartCallback(String callbackId);

}
