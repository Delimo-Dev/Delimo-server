package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Data;

@Data
public class ResolutionResponse {
    private int code;
    private String message;
    private Object data;

    public ResolutionResponse(){
        this.code = StatusCode.UNAUTHORIZED;
        this.message = ResponseMessage.NOT_FOUND_USER;
        this.data = null;
    }

}
