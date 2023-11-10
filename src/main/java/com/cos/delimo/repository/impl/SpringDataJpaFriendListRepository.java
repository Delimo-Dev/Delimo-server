package com.cos.delimo.repository.impl;

import com.cos.delimo.domain.FriendList;
import com.cos.delimo.repository.FriendListRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaFriendListRepository extends JpaRepository<FriendList, Long>, FriendListRepository {
    @Override
    FriendList save(@NotNull FriendList friendList);

    @Override
    void delete(@NotNull FriendList friendList);
}
