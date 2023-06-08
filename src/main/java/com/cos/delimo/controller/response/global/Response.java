package com.cos.delimo.controller.response.global;

import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class Response {
    private int code;
    private String message;
    private Object data;

    public Response(){
        this.code = StatusCode.UNAUTHORIZED;
        this.message = ResponseMessage.UNAUTHORIZED;
        this.data = null;
    }

    @Builder
    public Response(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseEntity<Response> unAuthorized(){
        setCode(StatusCode.UNAUTHORIZED);
        setMessage(ResponseMessage.UNAUTHORIZED);
        return new ResponseEntity<>(this, HttpStatus.UNAUTHORIZED);
    }
}
