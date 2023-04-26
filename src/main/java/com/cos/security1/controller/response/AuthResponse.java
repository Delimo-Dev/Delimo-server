package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthResponse {
    private int code;
    private String message;
    private Object data;

    public AuthResponse(){
        this.code = StatusCode.UNAUTHORIZED;
        this.message = ResponseMessage.UNAUTHORIZED;
        this.data = null;
    }

    @Builder
    public AuthResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
