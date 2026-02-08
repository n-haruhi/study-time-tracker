package com.studytracker.study_time_tracker.service;

import com.studytracker.study_time_tracker.entity.StudySession;
import com.studytracker.study_time_tracker.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudySessionService {
    
    private final StudySessionRepository repository;
    
    // 全件取得
    public List<StudySession> getAllSessions() {
        return repository.findAll();
    }
    
    public StudySession getSessionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }
    
    public StudySession createSession(StudySession session) {
        // 終了時刻が設定されている場合、自動で学習時間を計算
        calculateDuration(session);
        return repository.save(session);
    }
    
    public StudySession updateSession(Long id, StudySession session) {
        StudySession existing = getSessionById(id);
        existing.setStartTime(session.getStartTime());
        existing.setEndTime(session.getEndTime());
        existing.setCategory(session.getCategory());
        existing.setMemo(session.getMemo());
        // 終了時刻が設定されている場合、自動で学習時間を計算
        calculateDuration(existing);
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
    
    // 学習時間を自動計算するヘルパーメソッド
    private void calculateDuration(StudySession session) {
        if (session.getEndTime() != null && session.getStartTime() != null) {
            Duration duration = Duration.between(session.getStartTime(), session.getEndTime());
            session.setDurationMinutes((int) duration.toMinutes());
        }
    }
}