package com.example.CallbackApi.service;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.CallbackProcessedRequest;
import com.example.CallbackApi.dto.GetStatusResponse;
import com.example.CallbackApi.dto.StartCallbackRequest;

public interface CallbackApiService {

    String startCallback(StartCallbackRequest startCallbackRequest);

    void markCallbackAsStarted(String callbackId, BodyRequest callbackAcknowledgedRequest);

    void updateCallbackStatus(String callbackId, CallbackProcessedRequest callbackUpdateRequest);

    GetStatusResponse getCallbackStatus(String callbackId);
}
