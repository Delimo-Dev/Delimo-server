package com.cos.delimo.controller.response.community;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@EqualsAndHashCode(callSuper = false)
public class NewCommentResponse extends Response{

    public ResponseEntity<Response> commentSuccessful() {
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.DIARY_COMMENT_SUCCEESSFUL);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<Response> commentFailed() {
        setCode(StatusCode.UNAUTHORIZED);
        setMessage(ResponseMessage.COMMENT_FAILED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

}
