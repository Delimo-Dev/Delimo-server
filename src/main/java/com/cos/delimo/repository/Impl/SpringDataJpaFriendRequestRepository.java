package com.cos.delimo.repository.Impl;

import com.cos.delimo.domain.FriendRequest;
import com.cos.delimo.repository.FriendRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cos.delimo.domain.Member;


import java.util.List;

public interface SpringDataJpaFriendRequestRepository extends JpaRepository<FriendRequest, Long>, FriendRequestRepository {
    @Query(value = "select fr FROM FriendRequest fr where fr.requester = :requester and fr.requested = :requested")
    List<FriendRequest> findRequest(@Param("requester") Member requester, @Param("requested") Member requested);
}
