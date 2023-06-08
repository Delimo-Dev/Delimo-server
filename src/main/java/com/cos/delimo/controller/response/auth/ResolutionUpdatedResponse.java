package com.cos.delimo.controller.response.auth;

import com.cos.delimo.controller.response.global.Response;
import com.cos.delimo.controller.status.ResponseMessage;
import com.cos.delimo.controller.status.StatusCode;
import com.cos.delimo.dto.ResolutionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResolutionUpdatedResponse extends Response {
    public ResponseEntity<Response> resolutionUpdated(ResolutionDto resolutionDto){
        setCode(StatusCode.OK);
        setMessage(ResponseMessage.RESOLUTION_UPDATED);
        setData(resolutionDto);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }
}
