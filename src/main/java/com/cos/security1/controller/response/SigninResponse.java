package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class SigninResponse {
    private int code;
    private String message;
    private Object data;

    public SigninResponse(){
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.EMAIL_EXISTED;
        this.data = null;
    }

    @Builder
    public SigninResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
