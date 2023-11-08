package com.cos.delimo.repository;

import com.cos.delimo.domain.Diary;
import com.cos.delimo.domain.DiarySentiment;
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

    @Query("update Diary d set d.content = :content, d.privacy = :privacy, d.diarySentiment = :sentiment, d.visited = 0 where d.id = :id")
    @Modifying
    void updateDiary(@Param("id") Long id, @Param("content") String content, @Param("privacy") int privacy, @Param("sentiment") DiarySentiment diarySentiment);

    @Query("update Diary d set d.visited = d.visited + 1 where d.id = :id")
    @Modifying
    void updateVisited(@Param("id") Long id);
}
