package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.DiaryResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DiaryListResponse extends Response{
    public ResponseEntity<Response> diaryListSuccessful(List<DiaryResponseDto> diaries){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.DIARY_LIST_SUCCESSFUL);
        setData(diaries);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

}
