package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Builder;

public class FriendFoundResponse {
    private final int code;
    private final String message;
    private final Object data;


    public FriendFoundResponse(){
        this.code = StatusCode.NOT_FOUND;
        this.message = ResponseMessage.NOT_FOUND_USER;
        this.data = null;
    }

    @Builder
    public FriendFoundResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
