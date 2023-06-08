package com.cos.delimo.controller.response.auth;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.MyPageResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class MyPageResponse extends Response {
    public ResponseEntity<Response> getMypageSuccessful(MyPageResponseDto myPageResponseDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.MEMBER_INFO_SUCCESS);
        setData(myPageResponseDto);
        return new ResponseEntity<>(this, HttpStatus.OK);

    }
}
