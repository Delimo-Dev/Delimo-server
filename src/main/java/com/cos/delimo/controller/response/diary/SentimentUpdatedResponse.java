package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.DiarySentimentUpdateResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@EqualsAndHashCode(callSuper=false)
@Setter
public class SentimentUpdatedResponse extends Response {
    public ResponseEntity<Response> sentimentUpdated(DiarySentimentUpdateResponseDto diarySentimentUpdateResponseDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.SENTIMENT_UPDATED_SUCCEED);
        setData(diarySentimentUpdateResponseDto);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}