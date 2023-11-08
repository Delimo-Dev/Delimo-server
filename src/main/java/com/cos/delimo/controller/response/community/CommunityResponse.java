package com.cos.delimo.controller.response.community;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.CommunityDiaryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommunityResponse extends Response {

    public ResponseEntity<Response> communityGetSuccessful(List<CommunityDiaryDto> diaries) {
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.COMMUNITY_SUCCESSFUL);
        setData(diaries);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

}
