package com.example.CallbackApi.controller;

import com.example.CallbackApi.dto.BodyRequest;
import com.example.CallbackApi.dto.CallbackProcessedRequest;
import com.example.CallbackApi.dto.GetStatusResponse;
import com.example.CallbackApi.dto.StartCallbackRequest;
import com.example.CallbackApi.service.CallbackApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<String> markCallbackStarted(@PathVariable String callbackId, @RequestBody BodyRequest callbackAcknowledgedRequest) {
        callbackApiService.markCallbackAsStarted(callbackId, callbackAcknowledgedRequest);

        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/callback/{callbackId}")
    public ResponseEntity<String> updateCallbackStatus(@PathVariable String callbackId, @RequestBody CallbackProcessedRequest callbackUpdateRequest) {
        callbackApiService.updateCallbackStatus(callbackId, callbackUpdateRequest);

        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }

    /*"It will give us the status, detail and original body, as well as timestamps for when it was created and when the latest update occurred."
     There is no actual spot for the timestamps in the payload, so I'd have to follow up with the PM on that, clearly I could add it in my model but that would take more time*/
    @GetMapping("/callback/{callbackId}")
    public ResponseEntity<GetStatusResponse> getCallbackStatus(@PathVariable String callbackId) {
        return new ResponseEntity<GetStatusResponse>(
            callbackApiService.getCallbackStatus(callbackId),
            HttpStatus.OK);
    }
}
