package com.studytracker.study_time_tracker.service;

import com.studytracker.study_time_tracker.entity.StudySession;
import com.studytracker.study_time_tracker.repository.StudySessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudySessionServiceTest {
    
    @Mock
    private StudySessionRepository repository;
    
    @InjectMocks
    private StudySessionService service;
    
    @Test
    void 学習セッション作成_正常系() {
        // 準備
        StudySession session = new StudySession();
        session.setStartTime(LocalDateTime.of(2026, 2, 8, 10, 0));
        session.setEndTime(LocalDateTime.of(2026, 2, 8, 12, 30));
        session.setCategory("Java");
        
        StudySession savedSession = new StudySession();
        savedSession.setId(1L);
        savedSession.setStartTime(session.getStartTime());
        savedSession.setEndTime(session.getEndTime());
        savedSession.setDurationMinutes(150);
        savedSession.setCategory("Java");
        
        when(repository.save(any(StudySession.class))).thenReturn(savedSession);
        
        // 実行
        StudySession result = service.createSession(session);
        
        // 検証
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(150, result.getDurationMinutes());
        assertEquals("Java", result.getCategory());
        verify(repository, times(1)).save(any(StudySession.class));
    }
    
    @Test
    void 全件取得_正常系() {
        // 準備
        StudySession session1 = new StudySession();
        session1.setId(1L);
        session1.setCategory("Java");
        
        StudySession session2 = new StudySession();
        session2.setId(2L);
        session2.setCategory("React");
        
        when(repository.findAll()).thenReturn(Arrays.asList(session1, session2));
        
        // 実行
        List<StudySession> results = service.getAllSessions();
        
        // 検証
        assertEquals(2, results.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void 存在しないIDで取得_例外発生() {
        // 準備
        when(repository.findById(999L)).thenReturn(Optional.empty());
        
        // 実行 & 検証
        assertThrows(RuntimeException.class, () -> {
            service.getSessionById(999L);
        });
    }
}