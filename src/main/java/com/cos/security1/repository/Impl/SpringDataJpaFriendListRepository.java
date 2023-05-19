package com.cos.security1.repository.Impl;

import com.cos.security1.domain.FriendList;
import com.cos.security1.repository.FriendListRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaFriendListRepository extends JpaRepository<FriendList, Long>, FriendListRepository {
    @Override
    FriendList save(@NotNull FriendList friendList);

    @Override
    void delete(@NotNull FriendList friendList);
}
