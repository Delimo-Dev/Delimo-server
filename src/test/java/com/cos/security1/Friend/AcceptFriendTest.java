package com.cos.security1.Friend;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

@Transactional
public class AcceptFriendTest {

    @Test
    void 친구신청_승인(){
        // FriendRequest에서 삭제 후
        // requested와 requester에 해당하는 각 Member을 참조하는
        // FriendList에 추가하기 (friendId에 상대방 id 추가하기)
    }

    @Test
    void 친구신청_거절(){
        // FriendRequest에서 삭제
    }
}
