package com.cos.security1.repository.Impl;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.repository.FriendRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaFriendListRepository extends JpaRepository<FriendRequest, Long>, FriendRequestRepository {
    @Override
    FriendRequest save(FriendRequest friendRequest);

    @Override
    List<FriendRequest> findAllByRequester(Long requesterId);

    @Override
    List<FriendRequest> findAllByRequested(Long requestedId);

    @Override
    List<FriendRequest> findAllBy();
}
