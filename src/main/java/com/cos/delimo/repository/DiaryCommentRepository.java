package com.cos.delimo.repository;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Long> {
    List<DiaryComment> findAllBy();
    List<DiaryComment> findAllByDiary(Diary diary);
}
