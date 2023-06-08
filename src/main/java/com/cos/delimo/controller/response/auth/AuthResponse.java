package com.cos.delimo.controller.response.auth;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.LoginResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthResponse extends Response {
    public ResponseEntity<Response> loginFailed(){
        setMessage(ResponseMessage.LOGIN_FAILED);
        return new ResponseEntity<>(this, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Response> loginSuccessful(LoginResponseDto loginResponseDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.LOGIN_SUCCESS);
        setData(loginResponseDto);
        return new ResponseEntity<>(this, HttpStatus.ACCEPTED);

    }
}
