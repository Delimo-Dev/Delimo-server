package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class FriendRequestedResponse {
    private int code;
    private String message;
    private Object data;


    public FriendRequestedResponse(){
        this.code = StatusCode.NOT_FOUND;
        this.message = ResponseMessage.NOT_FOUND_USER;
        this.data = null;
    }

    @Builder
    public FriendRequestedResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
}
