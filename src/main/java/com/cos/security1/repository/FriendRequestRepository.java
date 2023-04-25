package com.cos.security1.repository;

import com.cos.security1.domain.FriendList;
import com.cos.security1.domain.FriendRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface FriendRequestRepository {
    // code로 검색한 후 신청 하기

    // 새로 friendRequest 만들기 (요청 들어 왔을 때)
    FriendRequest save(FriendRequest friendRequest);

    // 요청 승인 하는 경우 -> friendrequest에서 제거되고, friendlist에 새로 생성됨

    // 보낸 친구 신청 찾기 (requester이 자기 자신)


    List<FriendRequest> findAllByRequester(Long requesterId);

    // 요청 받은 친구 신청 목록 찾기 (requested가 자기 자신)
    List<FriendRequest> findAllByRequested(Long requestedId);
    List<FriendRequest> findAllBy();


}
