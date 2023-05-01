package com.cos.security1.repository.Impl;

import com.cos.security1.domain.FriendRequest;
import com.cos.security1.repository.FriendRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cos.security1.domain.Member;


import java.util.List;

public interface SpringDataJpaFriendRequestRepository extends JpaRepository<FriendRequest, Long>, FriendRequestRepository {
    @Override
    FriendRequest save(FriendRequest friendRequest);

    @Override
    List<FriendRequest> findAllByRequester(Long requesterId);

    @Override
    List<FriendRequest> findAllByRequested(Long requestedId);

    @Override
    List<FriendRequest> findAllBy();

    @Override
    void delete(FriendRequest friendRequest);

    @Query(value = "select fr FROM FriendRequest fr where fr.requester = :requester and fr.requested = :requested")
    List<FriendRequest> findRequest(@Param("requester") Member requester, @Param("requested") Member requested);
}
