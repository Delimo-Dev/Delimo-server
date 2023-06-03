package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class DiaryCreatedResponse {
    private final int code;
    private final String message;
    private final Object data;


    public DiaryCreatedResponse(){
        this.code = StatusCode.BAD_REQUEST;
        this.message = ResponseMessage.DIARY_NO_CONTENT;
        this.data = null;
    }

    @Builder
    public DiaryCreatedResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
