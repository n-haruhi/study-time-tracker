package com.studytracker.study_time_tracker.controller;

import com.studytracker.study_time_tracker.entity.StudySession;
import com.studytracker.study_time_tracker.service.StudySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class StudySessionController {
    
    private final StudySessionService service;
    
    // 全件取得
    @GetMapping
    public ResponseEntity<List<StudySession>> getAllSessions() {
        return ResponseEntity.ok(service.getAllSessions());
    }
    
    // ID指定で取得
    @GetMapping("/{id}")
    public ResponseEntity<StudySession> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSessionById(id));
    }
    
    // 新規作成
    @PostMapping
    public ResponseEntity<StudySession> createSession(@RequestBody StudySession session) {
        StudySession created = service.createSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    // 更新
    @PutMapping("/{id}")
    public ResponseEntity<StudySession> updateSession(
            @PathVariable Long id,
            @RequestBody StudySession session) {
        return ResponseEntity.ok(service.updateSession(id, session));
    }
    
    // 削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
    
    // カテゴリで検索
    @GetMapping("/category/{category}")
    public ResponseEntity<List<StudySession>> getSessionsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getSessionsByCategory(category));
    }
}