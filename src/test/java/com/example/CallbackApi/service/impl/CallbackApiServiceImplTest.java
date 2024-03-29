package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.CallbackProcessedRequest;
import com.example.CallbackApi.dto.GetStatusResponse;
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
class CallbackApiServiceImplTest { //I could obviously write more tests for edge cases (integration not just unit) and would for prod code but c'mon time ain't free

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

    @Test
    public void updateCallbackStatus_StatusMustExistTest() {
        final String callbackId = startCallbackTest();

        Callback callback = callbackRepository.findByCallbackId(callbackId);
        assertNotNull(callback);
        assertEquals(callback.getStatus(), StatusEnum.NOT_STARTED.toString());

        ResponseStatusException badRequestException = Assertions.assertThrows(ResponseStatusException.class, () -> {
            callbackApiService.updateCallbackStatus(callbackId, new CallbackProcessedRequest("not a Status", "details"));
        });

        assertEquals(badRequestException.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateCallbackStatusTest() {
        final String callbackId = startCallbackTest();

        Callback callback = callbackRepository.findByCallbackId(callbackId);
        assertNotNull(callback);
        assertEquals(callback.getStatus(), StatusEnum.NOT_STARTED.toString());

        callbackApiService.updateCallbackStatus(callbackId, new CallbackProcessedRequest(StatusEnum.PROCESSED.toString(), "details"));

        callback = callbackRepository.findByCallbackId(callbackId);
        assertEquals(callback.getStatus(), StatusEnum.PROCESSED.toString());
    }

    //and end to end unit test that procs all four of these in order is definitely important, I haven't gotten to the UI yet
    @Test
    public void getCallbackTest() {
        final String callbackId = startCallbackTest();

        Callback callback = callbackRepository.findByCallbackId(callbackId);
        assertNotNull(callback);
        assertEquals(callback.getStatus(), StatusEnum.NOT_STARTED.toString());

        GetStatusResponse getStatusResponse = callbackApiService.getCallbackStatus(callbackId);

        assertEquals(getStatusResponse.getStatus(), callback.getStatus());
        assertEquals(getStatusResponse.getBody(), getStatusResponse.getBody());
        assertEquals(getStatusResponse.getDetail(), getStatusResponse.getDetail());
    }
}
