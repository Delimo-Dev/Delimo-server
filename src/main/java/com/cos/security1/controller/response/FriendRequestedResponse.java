package com.cos.security1.controller.response;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
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
}
