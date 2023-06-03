package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Data;
import lombok.Setter;


@Data
@Setter
public class SentimentUpdatedResponse {
    private int code;
    private String message;
    private Object data;


    public SentimentUpdatedResponse(){
        this.code = StatusCode.OK;
        this.message = ResponseMessage.SENTIMENT_UPDATED_SUCCEED;
        this.data = null;
    }
}