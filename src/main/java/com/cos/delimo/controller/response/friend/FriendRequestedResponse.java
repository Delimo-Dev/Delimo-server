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
public class FriendRequestedResponse extends Response {
    public FriendRequestedResponse(){
        setCode(StatusCode.BAD_REQUEST);
    }
    public ResponseEntity<Response> friendNotFound() {
        setMessage(ResponseMessage.FRIEND_NOT_FOUND);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> requestFailed(){
        setMessage(ResponseMessage.REQUEST_FAILED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> alreadyFriend(){
        setMessage(ResponseMessage.FRIEND_INCLUDED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> requestExisted(){
        setMessage(ResponseMessage.REQUEST_EXISTED);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> requestSuccessful(){
        setCode(StatusCode.CREATED);
        setMessage(ResponseMessage.FRIEND_REQUESTED_SUCCESS);
        return new ResponseEntity<>(this, HttpStatus.CREATED);
    }
}
