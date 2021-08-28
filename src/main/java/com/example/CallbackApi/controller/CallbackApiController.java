package com.example.CallbackApi.controller;

import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.dto.StatusResponse;
import com.example.CallbackApi.service.CallbackApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackApiController {

    @Autowired
    CallbackApiService callbackApiService;

    @PostMapping("/request")
    public String callDownstreamService(@RequestBody StartCallbackRequest startCallbackRequest) {
        return callbackApiService.startCallback(startCallbackRequest);
//        return new ResponseEntity<StatusResponse>(new StatusResponse("ok"), HttpStatus.OK); //the spec said to return a string, fine, but a response body with {"type":"thirdPartyCallback", "id": "\{id\}"} might be nice context to give
    }

}
