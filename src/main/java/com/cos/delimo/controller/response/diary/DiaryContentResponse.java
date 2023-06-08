package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.DiaryResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class DiaryContentResponse extends Response {
    public ResponseEntity<Response> diaryNotCreated(){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.DIARY_NONE_TODAY);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<Response> diaryGetSuccessful(DiaryResponseDto diaryResponseDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.DIARY_CONTENT_SUCCESSFUL);
        setData(diaryResponseDto);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}
