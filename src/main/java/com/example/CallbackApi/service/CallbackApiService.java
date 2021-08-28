package com.example.CallbackApi.service;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.StartCallbackRequest;

public interface CallbackApiService {

    String startCallback(StartCallbackRequest startCallbackRequest);

    void markCallbackAsStarted(String callbackId, BodyRequest callbackAcknowledgedRequest);
}
