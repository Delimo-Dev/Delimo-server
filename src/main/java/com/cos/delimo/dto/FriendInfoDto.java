package com.cos.delimo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendInfoDto {
    private Long friendId;
    private String nickname;
    private String resolution;
}
