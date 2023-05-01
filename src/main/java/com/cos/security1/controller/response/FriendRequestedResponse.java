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
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.FRIEND_NOT_FOUND;
        this.data = null;
    }

    @Builder
    public FriendRequestedResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
}
