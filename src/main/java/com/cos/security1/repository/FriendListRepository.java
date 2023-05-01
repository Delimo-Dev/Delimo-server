package com.cos.security1.repository;

import com.cos.security1.domain.FriendList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface FriendListRepository {
    FriendList save(FriendList friendList);
}
