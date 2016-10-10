FORMAT: 1A

## V1 API リファレンス

### URI情報

| URI情報    | 値     |
| :------------- | :------------- |
| Host         | xxxxxxxxx（未定） |
| Protocol     | HTTP  |
| Base URI     | http://xxxxxxxxx/api/master/v1 |


### アクセスURI一覧

| HTTP method | URI                          | 用途           |
| :---------- | :--------------------------- | :------------- |
| GET         | /magazine/list               | Mandalaが保持している雑誌情報の一覧取得 |
| GET         | /magazine/:name/info         | nameで指定した雑誌の保持データの一覧取得 |
| GET         | /magazine/:name/latest       | nameで指定した雑誌の最新情報の取得 |
| GET         | /magazine/:name/:year        | nameで指定した雑誌のyearの年に持つ情報の取得 |
| GET         | /magazine/:name/:year/:month | nameで指定した雑誌のyearの年のmonthの月に持つ情報の取得 |


### GET /magazine/list

Mandalaが保持している、雑誌情報（発行タイミングによる分類、情報の年月、雑誌名、最新情報）のリストを返却します。

#### Request

なし

#### Response

以下のデータの配列をJSONで返却します。  
データが無い場合は空のJSONデータ（[]）を返却します。

| JSON Key     | 型     | サイズ | 必須 | 説明      | Sample    |
| :----------- | :----- | :--- | :-- | :-------- | :-------- |
| title        | String | 50   | ○   | 雑誌のタイトル名 | 週刊少年ジャンプ |
| title_path   | String | 20   | ○   | 雑誌データの検索用パス名 | jump |
| kind         | String | 3    | ○   | 雑誌の種類（不明、冊子、WEBのいずれか） | 冊子 |
| latest_data  | String | 7    | ○   | 保持している最新データの年月 | 2016-09 |



### GET /magazine/:name/info

nameで指定したMandalaが保持している雑誌情報を返却します。

#### Request

| param     | 型     | サイズ | 必須 | 説明      | Sample    |
| :-------- | :----- | :--- | :-- | :-------- | :-------- |
| name      | String | 20   | ○   | URIに設定する雑誌名を表すパス | jump |


#### Response

以下のデータの配列をJSONで返却します。  
データが無い場合は空のJSONデータ（[]）を返却します。

| JSON Key     | 型     | サイズ | 必須 | 説明      | Sample    |
| :----------- | :----- | :--- | :-- | :-------- | :-------- |


`id` '雑誌ID',
`title` 'タイトル',
`title_path` 'タイトルパス',
`publisher_name` '出版社名',
`public_url` VARCHAR(200) DEFAULT NULL COMMENT '公式URL',
`twitter_account` VARCHAR(20) DEFAULT NULL COMMENT 'Twitterアカウント',
`twitter_hash_tag` VARCHAR(20) DEFAULT NULL COMMENT 'Twitterハッシュタグ',
`kind` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '分類',
`release_timing` TINYINT(2) NOT NULL DEFAULT 0 COMMENT 'リリースタイミング',
`status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'ステータス',
`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',



### GET /magazine/:name/latest

nameで指定した雑誌について、Mandalaで保持している最新の漫画情報を返却する。

#### Request

| param     | 型     | サイズ | 必須 | 説明      | Sample    |
| :-------- | :----- | :--- | :-- | :-------- | :-------- |
| name      | String | 20   | ○   | URIに設定する雑誌名を表すパス | jump |


#### Response

以下のデータの配列をJSONで返却します。  
データが無い場合は空のJSONデータ（[]）を返却します。

| JSON Key     | 型     | サイズ | 必須 | 説明      | Sample    |
| :----------- | :----- | :--- | :-- | :-------- | :-------- |
| title        | String | 50   | ○   | 雑誌のタイトル名 | 週刊少年ジャンプ |
| title_path   | String | 20   | ○   | 雑誌データの検索用パス名 | jump |
| kind         | String | 3    | ○   | 雑誌の種類（不明、冊子、WEBのいずれか） | 冊子 |
| latest_data  | String | 7    | ○   | 保持している最新データの年月 | 2016-09 |
`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '漫画ID',
{
  id 漫画ID,
  title 漫画タイトル,
  title_short 漫画タイトル略称,
  author '作者',
  original_author '原作',
  support_author 'キャラクターデザインまたは原案',
  public_url '公式URL',
  twitter_account 'Twitterアカウント',
  twitter_hash_tag 'Twitterハッシュタグ',
  target_sex '対象性別',
  release_status '連載状況',
  ?`created_at` '作成日時',
  updated_at '更新日時',

