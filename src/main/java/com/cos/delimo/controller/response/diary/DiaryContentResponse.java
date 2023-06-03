package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryContentResponse {
    private final int code;
    private final String message;
    private final Object data;


    public DiaryContentResponse(){
        this.code = StatusCode.OK;
        this.message = ResponseMessage.DIARY_NO_CONTENT;
        this.data = null;
    }

    @Builder
    public DiaryContentResponse(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
