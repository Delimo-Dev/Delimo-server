package com.cos.delimo.repository;


import com.cos.delimo.domain.FriendRequest;
import com.cos.delimo.domain.Member;
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
