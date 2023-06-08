package com.cos.delimo.controller.response.friend;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.FriendInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class FriendFoundResponse extends Response {
    public ResponseEntity<Response> userNotFound(){
        setCode(StatusCode.NOT_FOUND);
        setMessage(ResponseMessage.NOT_FOUND_USER);
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> userFound(FriendInfoDto friendInfoDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.FRIEND_FOUND);
        setData(friendInfoDto);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}
