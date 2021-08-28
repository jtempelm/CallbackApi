package com.example.CallbackApi.gateway;

import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import org.springframework.stereotype.Service;

public interface ThirdPartyServiceGateway {

    String thirdPartyStartCallback(ThirdPartyCallbackRequest thirdPartyCallbackRequest);

}
