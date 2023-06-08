package com.cos.delimo.controller.response.diary;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.DiaryDto;
import com.cos.delimo.dto.DiaryResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
@Setter
public class DiaryCreatedResponse extends Response {
   public ResponseEntity<Response> diaryNoContent(){
       setCode(StatusCode.NO_CONTENT);
       setMessage(ResponseMessage.DIARY_NO_CONTENT);
       return new ResponseEntity<>(this, HttpStatus.NO_CONTENT);
   }

   public ResponseEntity<Response> diaryCreated(DiaryResponseDto diaryResponseDto){
       setCode(StatusCode.CREATED);
       setMessage(ResponseMessage.DIARY_CREATED);
       setData(diaryResponseDto);
       return new ResponseEntity<>(this, HttpStatus.CREATED);
   }

}
