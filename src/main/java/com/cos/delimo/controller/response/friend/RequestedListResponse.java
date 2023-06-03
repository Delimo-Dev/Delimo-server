package com.cos.delimo.controller.response.friend;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class RequestedListResponse {
    int code;
    String message;
    Object data;


    public RequestedListResponse(){
        this.code = StatusCode.CREATED;
        this.message = ResponseMessage.FRIEND_REQUEST_LIST_SUCCESSFUL;
        this.data = null;
    }

    @Builder
    public RequestedListResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
