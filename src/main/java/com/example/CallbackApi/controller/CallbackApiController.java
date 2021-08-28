package com.example.CallbackApi.controller;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.service.CallbackApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackApiController {

    @Autowired
    CallbackApiService callbackApiService;

    @PostMapping("/request")
    public ResponseEntity<String> callDownstreamService(@RequestBody StartCallbackRequest startCallbackRequest) {
        return new ResponseEntity<String>(
            callbackApiService.startCallback(startCallbackRequest),
            HttpStatus.OK
        );
//        return new ResponseEntity<StatusResponse>(new StatusResponse("ok"), HttpStatus.OK); //the spec said to return a string, fine, but a response body with {"type":"thirdPartyCallback", "id": "\{id\}"} might be nice context to give
    }

    @PostMapping("/callback/{callbackId}") //Afterward, the service will send an initial POST with the text string `STARTED` to indicate -it's- they received the request. ? typo in spec "it's"
    public ResponseEntity<String> callbackStarted(@PathVariable String callbackId, @RequestBody BodyRequest callbackAcknowledgedRequest) {
        callbackApiService.markCallbackAsStarted(callbackId, callbackAcknowledgedRequest);

        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

}
