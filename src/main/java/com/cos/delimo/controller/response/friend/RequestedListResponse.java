package com.cos.delimo.controller.response.friend;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.FriendInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RequestedListResponse extends Response {
    public ResponseEntity<Response> requestListSuccessful(List<FriendInfoDto> friendInfoDtoList){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.FRIEND_REQUEST_LIST_SUCCESSFUL);
        setData(friendInfoDtoList);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

}
