package com.cos.security1.controller.response.friend;

import com.cos.security1.controller.status.ResponseMessage;
import com.cos.security1.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class RejectFriendResponse {
    private final int code;
    private final String message;
    private final Object data;


    public RejectFriendResponse(){
        this.code = StatusCode.CREATED;
        this.message = ResponseMessage.FRIEND_REQUEST_REJECTED;
        this.data = null;
    }

    @Builder
    public RejectFriendResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
