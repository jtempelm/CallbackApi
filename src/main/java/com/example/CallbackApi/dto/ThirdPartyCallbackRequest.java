package com.example.CallbackApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThirdPartyCallbackRequest extends BodyRequest {

    String callback;

}
