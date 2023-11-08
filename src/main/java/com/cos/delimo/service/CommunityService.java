package com.cos.delimo.service;

import com.cos.delimo.domain.DiaryComment;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.CommunityDiaryDto;

import java.util.List;

public interface CommunityService {
    List<CommunityDiaryDto> getAllDiaries();
    DiaryComment insertComment(Long diaryId, Member member, String content);
}
