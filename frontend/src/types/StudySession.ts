export interface StudySession {
  id?: number;
  startTime: string;
  endTime: string | null;
  durationMinutes: number | null;
  category: string;
  memo: string | null;
  createdAt?: string;
  updatedAt?: string;
}
