package com.cos.delimo.controller.response.friend;

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
public class RejectFriendResponse extends Response {
    public ResponseEntity<Response> friendNotFound(){
        setCode(StatusCode.NOT_FOUND);
        setMessage(ResponseMessage.FRIEND_NOT_FOUND);
        return new ResponseEntity<>(this, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Response> requestRejectFailed(){
        setCode(StatusCode.BAD_REQUEST);
        setMessage(ResponseMessage.FRIEND_REQUEST_ACCEPTED_FAILED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> rejectRequest(){
        setCode(StatusCode.CREATED);
        setMessage(ResponseMessage.FRIEND_REQUEST_REJECTED);
        return new ResponseEntity<>(this, HttpStatus.CREATED);
    }
}
