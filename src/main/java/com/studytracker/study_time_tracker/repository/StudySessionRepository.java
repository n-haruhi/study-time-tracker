package com.studytracker.study_time_tracker.repository;

import com.studytracker.study_time_tracker.entity.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    
    // カテゴリで検索
    List<StudySession> findByCategory(String category);
    
    // 期間で検索
    List<StudySession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    // カテゴリ別の合計時間を取得（将来の集計用）
    @Query("SELECT s.category, SUM(s.durationMinutes) FROM StudySession s GROUP BY s.category")
    List<Object[]> getTotalTimeByCategory();
}