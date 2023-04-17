package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
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
}
