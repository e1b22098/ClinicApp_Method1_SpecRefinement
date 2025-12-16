# セットアップ手順

診療予約管理システムのセットアップを順番に実行してください。

## 前提条件

- Java 17 がインストールされていること
- MySQL がインストールされ、起動していること
- Maven がインストールされていること（またはIDEのMaven機能を使用）

## セットアップ手順

### ステップ1: MySQLの接続確認とデータベース作成

1. MySQLにログインします：
   ```bash
   mysql -u root -p
   ```

2. `setup/01_create_database.sql` を実行します：
   ```sql
   source setup/01_create_database.sql
   ```
   または、MySQLクライアントでファイルの内容をコピー&ペーストして実行してください。

3. データベースが作成されたことを確認します：
   ```sql
   SHOW DATABASES;
   ```

### ステップ2: テーブルの作成と初期データ投入

1. `setup/02_create_tables.sql` を実行します：
   ```sql
   USE clinic_booking_db;
   source setup/02_create_tables.sql;
   ```

2. テーブルが作成されたことを確認します：
   ```sql
   USE clinic_booking_db;
   SHOW TABLES;
   ```

3. 初期データが投入されたことを確認します：
   ```sql
   SELECT * FROM admins;
   SELECT * FROM time_slots;
   ```

### ステップ3: アプリケーション設定の確認

1. `src/main/resources/application.properties` を開きます

2. データベース接続情報を確認・編集します：
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
   `your_password` を実際のMySQLのパスワードに変更してください。

### ステップ4: Maven依存関係のインストール

プロジェクトルートで以下のコマンドを実行：

```bash
mvn clean install
```

または、Eclipseを使用している場合は：
- プロジェクトを右クリック → `Maven` → `Update Project...`
- `Force Update of Snapshots/Releases` にチェック → `OK`

### ステップ5: アプリケーションのビルド

```bash
mvn clean package
```

### ステップ6: アプリケーションの起動

#### 方法1: Mavenコマンドで起動
```bash
mvn spring-boot:run
```

#### 方法2: Eclipseから起動
1. `ClinicBookingApplication.java` を右クリック
2. `Run As` → `Java Application`

#### 方法3: ビルドしたJARファイルで起動
```bash
java -jar target/clinic-booking-system-1.0.0.jar
```

### ステップ7: 動作確認

1. ブラウザで以下のURLにアクセス：
   - http://localhost:8080
   - http://localhost:8080/login

2. 管理者でログイン：
   - 管理者ID: `admin`
   - パスワード: `admin123`

3. 患者機能をテストする場合は、新規登録画面から患者を登録してください。

## トラブルシューティング

### MySQL接続エラー
- MySQLが起動しているか確認
- `application.properties` の接続情報が正しいか確認
- ファイアウォールの設定を確認

### ポート8080が使用中
- `application.properties` で `server.port` を変更
- または、使用中のプロセスを終了

### テーブルが見つからない
- `USE clinic_booking_db;` でデータベースを選択しているか確認
- `setup/02_create_tables.sql` が正常に実行されたか確認

