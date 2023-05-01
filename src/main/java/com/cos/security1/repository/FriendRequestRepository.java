package com.cos.security1.repository;


import com.cos.security1.domain.FriendRequest;
import com.cos.security1.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface FriendRequestRepository {
    FriendRequest save(FriendRequest friendRequest);
    List<FriendRequest> findRequest(Member requester, Member requested);
    List<FriendRequest> findAllByRequester(Long requesterId);
    List<FriendRequest> findAllByRequested(Long requestedId);
    List<FriendRequest> findAllBy();
    void delete(FriendRequest friendRequest);


}
