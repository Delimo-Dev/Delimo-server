package com.cos.security1.repository.Impl;

import com.cos.security1.domain.FriendList;
import com.cos.security1.repository.FriendListRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFriendListRepository extends JpaRepository<FriendList, Long>, FriendListRepository {
    @Override
    FriendList save(FriendList friendList);

    @Override
    void delete(FriendList friendList);
}
