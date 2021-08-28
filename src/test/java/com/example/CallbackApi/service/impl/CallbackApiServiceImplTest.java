package com.example.CallbackApi.service.impl;

import com.example.CallbackApi.dto.ThirdPartyCallbackRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CallbackApiServiceImplTest { //I could obviously write more tests for edge cases and would for prod code but c'mon time ain't free

    @Autowired
    CallbackApiServiceImpl callbackApiService;

    @Test
    void contextLoads() {
    }

    @Test
    public void getThirdPartyCallbackRequestTest() {
        final String TEST_BODY = "testBody";
        ThirdPartyCallbackRequest thirdPartyCallbackRequest = callbackApiService.getThirdPartyCallbackRequest(TEST_BODY, "callbackId");

        assertNotNull(thirdPartyCallbackRequest);
        assertEquals(thirdPartyCallbackRequest.getBody(), TEST_BODY);
        assertEquals(thirdPartyCallbackRequest.getCallback(), "/callback/callbackId");
    }
}
