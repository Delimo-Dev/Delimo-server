package com.cos.delimo.controller.response.auth;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class EmailVerificationResponse {
    private int code;
    private String message;
    private Object data;

    public EmailVerificationResponse(){
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.EMAIL_EXISTED;
        this.data = null;
    }

    @Builder
    public EmailVerificationResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
