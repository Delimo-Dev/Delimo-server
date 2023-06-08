package com.cos.delimo.controller.response.auth;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmailVerificationResponse extends Response {
    public ResponseEntity<Response> emailExisted(){
        setCode(StatusCode.BAD_REQUEST);
        setMessage(ResponseMessage.EMAIL_EXISTED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> emailAvailable(){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.EMAIL_OK);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}
