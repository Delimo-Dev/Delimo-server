package com.cos.security1.repository;

import com.cos.security1.domain.DiarySentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiarySentimentRepository extends JpaRepository<DiarySentiment, Long> {
    @Query("update DiarySentiment ds set ds.sentiment = :sentiment where ds.id = :id")
    @Modifying
    void updateSentiment(@Param("id") Long id, @Param("sentiment") int sentiment);
}
