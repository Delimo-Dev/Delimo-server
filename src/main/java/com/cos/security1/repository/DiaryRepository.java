package com.cos.security1.repository;

import com.cos.security1.domain.Diary;
import com.cos.security1.domain.DiarySentiment;
import com.cos.security1.domain.Member;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @NotNull
    Diary save(@NotNull Diary diary);
    List<Diary> findAllBy();
    List<Diary> findAllByMember (Member member);

    @Query("update Diary d set d.content = :content, d.privacy = :privacy, d.diarySentiment = :sentiment where d.id = :id")
    @Modifying
    void updateDiary(@Param("id") Long id, @Param("content") String content, @Param("privacy") int privacy, @Param("sentiment") DiarySentiment diarySentiment);
}
