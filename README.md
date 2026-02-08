# RhythmNote
学習時間を記録・管理するWebアプリケーション

## 概要
日々の学習時間を記録し、カテゴリ別に管理できるシステムです。
学習の開始・終了時刻を記録すると、自動で学習時間を計算します。

## 技術スタック
### バックエンド
- Java 21
- Spring Boot 3.5.10
- PostgreSQL 16
- Maven
- JUnit 5
- Mockito

### フロントエンド
- React 18
- TypeScript
- Vite
- Axios
- Lucide React (アイコン)

## 主な機能
- 学習セッションの登録・取得・更新・削除
- 学習時間の自動計算
- カテゴリ別の検索機能
- モダンなUIでの直感的な操作

## セットアップ
### 前提条件
- Java 21以上
- PostgreSQL 16以上
- Maven
- Node.js 18以上

### データベースの準備
```bash
# PostgreSQLにログイン
sudo -u postgres psql

# データベースとユーザーを作成
CREATE DATABASE study_time_tracker;
CREATE USER studyuser WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE study_time_tracker TO studyuser;
GRANT ALL PRIVILEGES ON SCHEMA public TO studyuser;
```

### バックエンドの起動
```bash
# リポジトリをクローン
git clone https://github.com/n-haruhi/study-time-tracker.git
cd study-time-tracker

# アプリケーションを起動
./mvnw spring-boot:run
```

起動後、`http://localhost:8080` でAPIにアクセス可能

### フロントエンドの起動
```bash
# フロントエンドディレクトリに移動
cd frontend

# 依存関係をインストール
npm install

# 開発サーバーを起動
npm run dev
```

起動後、`http://localhost:5173` でアクセス

## API仕様
### 学習セッション登録
```bash
POST /api/sessions
Content-Type: application/json

{
  "startTime": "2026-02-08T10:00:00",
  "endTime": "2026-02-08T12:30:00",
  "category": "Java",
  "memo": "Spring Boot学習"
}
```

### 全件取得
```bash
GET /api/sessions
```

### ID指定で取得
```bash
GET /api/sessions/{id}
```

### カテゴリ検索
```bash
GET /api/sessions/category/{category}
```

### 更新
```bash
PUT /api/sessions/{id}
Content-Type: application/json

{
  "startTime": "2026-02-08T10:00:00",
  "endTime": "2026-02-08T13:00:00",
  "category": "Java",
  "memo": "Spring Boot学習（更新）"
}
```

### 削除
```bash
DELETE /api/sessions/{id}
```

## テストの実行
```bash
# バックエンドのテスト
./mvnw test
```
