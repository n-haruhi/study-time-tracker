import api from './axios';
import type { StudySession } from '../types/StudySession';

export const studySessionApi = {
  // 全件取得
  getAll: () => api.get<StudySession[]>('/sessions'),
  
  // ID指定取得
  getById: (id: number) => api.get<StudySession>(`/sessions/${id}`),
  
  // 新規作成
  create: (session: StudySession) => api.post<StudySession>('/sessions', session),
  
  // 更新
  update: (id: number, session: StudySession) => 
    api.put<StudySession>(`/sessions/${id}`, session),
  
  // 削除
  delete: (id: number) => api.delete(`/sessions/${id}`),
  
  // カテゴリ検索
  getByCategory: (category: string) => 
    api.get<StudySession[]>(`/sessions/category/${category}`),
};