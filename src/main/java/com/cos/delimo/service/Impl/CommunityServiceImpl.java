package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.DiaryComment;
import com.cos.delimo.domain.Member;
import com.cos.delimo.dto.CommunityDiaryDto;
import com.cos.delimo.repository.DiaryCommentRepository;
import com.cos.delimo.repository.DiaryRepository;
import com.cos.delimo.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final DiaryRepository diaryRepository;
    private final DiaryCommentRepository commentRepository;

    @Autowired
    public CommunityServiceImpl(DiaryRepository diaryRepository, DiaryCommentRepository commentRepository) {
        this.diaryRepository = diaryRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 모든 일기 목록과 각각의 댓글 가져오기 (공객된 일기에 대해서만)
     * @return
     */

    @Override
    public List<CommunityDiaryDto> getAllDiaries() {
        List<CommunityDiaryDto> boardDiaries = new ArrayList<>();


        List<Diary> diaries = diaryRepository.findAll();

        for(Diary diary:diaries) {
            if (diary.getPrivacy() == 2) {
                Member member = diary.getMember();
                List<DiaryComment> comments = commentRepository.findAllByDiary(diary);

                CommunityDiaryDto diaryDto = CommunityDiaryDto.builder()
                        .diaryId(diary.getId())
                        .memberId(member.getId())
                        .code(member.getCode())
                        .nickname(member.getNickname())
                        .content(diary.getContent())
                        .createdDate(diary.getCreatedDate())
                        .comments(comments)
                    .build();

                boardDiaries.add(diaryDto);

            }
        }
        return boardDiaries;
    }

    @Override
    public void insertComment(Long diaryId, Long memberId, String content) {

        Optional<Diary> diary = diaryRepository.findById(diaryId);
        DiaryComment comment = DiaryComment.builder()
                .diary(diary.get())
                .memberId(memberId)
                .content(content)
                .build();

        commentRepository.save(comment);
    }

}
