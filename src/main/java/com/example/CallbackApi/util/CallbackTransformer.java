package com.example.CallbackApi.util;

import com.example.CallbackApi.dto.GetStatusResponse;
import com.example.CallbackApi.model.Callback;

public class CallbackTransformer {

    public static GetStatusResponse callbackToGetStatusResponse(Callback callback) {
        GetStatusResponse getStatusResponse = new GetStatusResponse();
        getStatusResponse.setStatus(callback.getStatus());
        getStatusResponse.setDetail(callback.getDetail());
        getStatusResponse.setBody(callback.getBody());
        return getStatusResponse;
    }

}
