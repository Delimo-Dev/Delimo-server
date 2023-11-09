package com.cos.delimo.service;

import com.cos.delimo.dto.CommunityDiaryDto;

import java.util.List;

public interface CommunityService {
    List<CommunityDiaryDto> getAllDiaries();
    void insertComment(Long diaryId, Long memberId, String content);
}
