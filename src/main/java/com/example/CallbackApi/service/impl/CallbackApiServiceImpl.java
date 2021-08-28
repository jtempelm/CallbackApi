package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import com.example.CallbackApi.gateway.ThirdPartyServiceGateway;
import com.example.CallbackApi.model.Callback;
import com.example.CallbackApi.repository.CallbackRepository;
import com.example.CallbackApi.service.CallbackApiService;
import com.example.CallbackApi.util.StatusEnum;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CallbackApiServiceImpl implements CallbackApiService {

    @Autowired
    ThirdPartyServiceGateway thirdPartyServiceGateway;

    @Autowired
    CallbackRepository callbackRepository;

    @Override
    public String startCallback(final StartCallbackRequest startCallbackRequest) {
        final String callbackId = UUID.randomUUID().toString();

        Callback callback = new Callback();
        callback.setCallbackId(callbackId);
        callback.setStatus(StatusEnum.NOT_STARTED.toString());
        callbackRepository.save(callback);

        thirdPartyServiceGateway.thirdPartyStartCallback(
            getThirdPartyCallbackRequest(startCallbackRequest.getBody(), callbackId)
        );

        return callbackId;
    }

    @Override
    public void markCallbackAsStarted(final String callbackId, final BodyRequest callbackAcknowledgedRequest) {
        Callback callback = callbackRepository.findByCallbackId(callbackId);
        if (callback == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No callback by that id");
        }

        callback.setStatus(StatusEnum.STARTED.toString()); //what if it was already started? Well we could handle that too
        callbackRepository.save(callback);
    }

    protected ThirdPartyCallbackRequest getThirdPartyCallbackRequest(final String body, final String callbackId) {
        ThirdPartyCallbackRequest thirdPartyCallbackRequest = new ThirdPartyCallbackRequest();
        thirdPartyCallbackRequest.setBody(body);
        thirdPartyCallbackRequest.setCallback("/callback/" + callbackId); //putting part of a uri in a request seems weird so I'd probably ask whoever made the spec about that

        return thirdPartyCallbackRequest;
    }
}
