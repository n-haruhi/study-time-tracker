package com.studytracker.study_time_tracker.service;

import com.studytracker.study_time_tracker.entity.StudySession;
import com.studytracker.study_time_tracker.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudySessionService {
    
    private final StudySessionRepository repository;
    
    // 全件取得
    public List<StudySession> getAllSessions() {
        return repository.findAll();
    }
    
    // ID指定で取得
    public StudySession getSessionById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Session not found: " + id));
    }
    
    // 新規作成
    public StudySession createSession(StudySession session) {
        // 終了時刻がある場合、学習時間を自動計算
        if (session.getEndTime() != null && session.getStartTime() != null) {
            long minutes = java.time.Duration.between(
                session.getStartTime(), 
                session.getEndTime()
            ).toMinutes();
            session.setDurationMinutes((int) minutes);
        }
        return repository.save(session);
    }
    
    // 更新
    public StudySession updateSession(Long id, StudySession updatedSession) {
        StudySession existing = getSessionById(id);
        existing.setStartTime(updatedSession.getStartTime());
        existing.setEndTime(updatedSession.getEndTime());
        existing.setCategory(updatedSession.getCategory());
        existing.setMemo(updatedSession.getMemo());
        
        // 学習時間を再計算
        if (existing.getEndTime() != null && existing.getStartTime() != null) {
            long minutes = java.time.Duration.between(
                existing.getStartTime(), 
                existing.getEndTime()
            ).toMinutes();
            existing.setDurationMinutes((int) minutes);
        }
        
        return repository.save(existing);
    }
    
    // 削除
    public void deleteSession(Long id) {
        repository.deleteById(id);
    }
    
    // カテゴリで検索
    public List<StudySession> getSessionsByCategory(String category) {
        return repository.findByCategory(category);
    }
    
    // 期間で検索
    public List<StudySession> getSessionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByStartTimeBetween(start, end);
    }
}