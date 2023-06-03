package com.cos.delimo.controller.response.friend;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
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
