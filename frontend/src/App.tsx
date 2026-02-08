import { useState, useEffect } from 'react';
import { studySessionApi } from './api/studySessionApi';
import type { StudySession } from './types/StudySession';
import './App.css';

function App() {
  const [sessions, setSessions] = useState<StudySession[]>([]);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    category: '',
    startTime: '',
    endTime: '',
    memo: '',
  });

  const fetchSessions = async () => {
    try {
      setLoading(true);
      const response = await studySessionApi.getAll();
      setSessions(response.data);
    } catch (error) {
      console.error('取得エラー:', error);
      alert('データの取得に失敗しました');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSessions();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await studySessionApi.create({
        category: formData.category,
        startTime: formData.startTime,
        endTime: formData.endTime || null,
        durationMinutes: null,
        memo: formData.memo || null,
      });
      alert('✓ 登録しました');
      setFormData({
        category: '',
        startTime: '',
        endTime: '',
        memo: '',
      });
      fetchSessions();
    } catch (error) {
      console.error('登録エラー:', error);
      alert('登録に失敗しました');
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('この記録を削除してもよろしいですか？')) return;
    try {
      await studySessionApi.delete(id);
      alert('✓ 削除しました');
      fetchSessions();
    } catch (error) {
      console.error('削除エラー:', error);
      alert('削除に失敗しました');
    }
  };

  const formatDateTime = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleString('ja-JP', {
      month: 'numeric',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) return <div>読み込み中...</div>;

  return (
    <div className="App">
      <h1>RhythmNote</h1>

      <div className="form-container">
        <h2>記録を追加</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="category">カテゴリ</label>
            <input
              id="category"
              type="text"
              placeholder="例: Java学習、英語、数学"
              value={formData.category}
              onChange={(e) => setFormData({ ...formData, category: e.target.value })}
              required
            />
          </div>
          <div>
            <label htmlFor="startTime">開始時刻</label>
            <input
              id="startTime"
              type="datetime-local"
              value={formData.startTime}
              onChange={(e) => setFormData({ ...formData, startTime: e.target.value })}
              required
            />
          </div>
          <div>
            <label htmlFor="endTime">終了時刻</label>
            <input
              id="endTime"
              type="datetime-local"
              value={formData.endTime}
              onChange={(e) => setFormData({ ...formData, endTime: e.target.value })}
            />
          </div>
          <div>
            <label htmlFor="memo">メモ（任意）</label>
            <textarea
              id="memo"
              placeholder="学習内容や気づいたことをメモ..."
              value={formData.memo}
              onChange={(e) => setFormData({ ...formData, memo: e.target.value })}
            />
          </div>
          <button type="submit">保存</button>
        </form>
      </div>

      <div className="list-container">
        <h2>学習記録</h2>
        {sessions.length === 0 ? (
          <p>まだ記録がありません。上のフォームから追加しましょう。</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>カテゴリ</th>
                <th>開始</th>
                <th>終了</th>
                <th>時間</th>
                <th>メモ</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {sessions.map((session) => (
                <tr key={session.id}>
                  <td>{session.category}</td>
                  <td>{formatDateTime(session.startTime)}</td>
                  <td>{session.endTime ? formatDateTime(session.endTime) : '-'}</td>
                  <td>{session.durationMinutes ? `${session.durationMinutes}分` : '-'}</td>
                  <td>{session.memo || '-'}</td>
                  <td>
                    <button onClick={() => handleDelete(session.id!)}>削除</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default App;