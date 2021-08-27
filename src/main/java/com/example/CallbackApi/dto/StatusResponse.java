package com.example.CallbackApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusResponse {

    String status;

    public StatusResponse(final String status) {
        this.status = status;
    }
}
