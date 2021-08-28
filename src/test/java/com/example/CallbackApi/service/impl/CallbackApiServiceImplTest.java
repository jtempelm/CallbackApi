package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import com.example.CallbackApi.model.Callback;
import com.example.CallbackApi.repository.CallbackRepository;
import com.example.CallbackApi.util.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CallbackApiServiceImplTest { //I could obviously write more tests for edge cases and would for prod code but c'mon time ain't free

    @Autowired
    CallbackApiServiceImpl callbackApiService;

    @Autowired
    CallbackRepository callbackRepository;

    private final String TEST_CALLBACK_ID = "callbackId";

    @Test
    void contextLoads() {
    }

    @Test
    public String startCallbackTest() {
        StartCallbackRequest startCallbackRequest = new StartCallbackRequest();
        startCallbackRequest.setBody("body");

        final String callbackId = callbackApiService.startCallback(startCallbackRequest);

        assertNotNull(callbackId);

        return callbackId;
    }

    @Test
    public void getThirdPartyCallbackRequestTest() {
        final String TEST_BODY = "testBody";
        ThirdPartyCallbackRequest thirdPartyCallbackRequest = callbackApiService.getThirdPartyCallbackRequest(TEST_BODY, TEST_CALLBACK_ID);

        assertNotNull(thirdPartyCallbackRequest);
        assertEquals(thirdPartyCallbackRequest.getBody(), TEST_BODY);
        assertEquals(thirdPartyCallbackRequest.getCallback(), "/callback/" + TEST_CALLBACK_ID);
    }

    @Test
    public void markCallbackAsStarted_MustHaveValidIdTest() {
        ResponseStatusException notFoundException = Assertions.assertThrows(ResponseStatusException.class, () -> {
            callbackApiService.markCallbackAsStarted("totallyNotInTheDbId", new BodyRequest());
        });

        assertEquals(notFoundException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void markCallbackAsStartedTest() {
        final String callbackId = startCallbackTest();

        Callback callback = callbackRepository.findByCallbackId(callbackId);
        assertNotNull(callback);
        assertEquals(callback.getStatus(), StatusEnum.NOT_STARTED.toString());

        callbackApiService.markCallbackAsStarted(callbackId, new BodyRequest());

        callback = callbackRepository.findByCallbackId(callbackId);
        assertEquals(callback.getStatus(), StatusEnum.STARTED.toString());
    }
}
