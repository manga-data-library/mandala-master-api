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
| GET         | /magazine/:name/:year        | nameで指定した雑誌のyearの年に持つ情報の取得 |
| GET         | /magazine/:name/:year/:month | nameで指定した雑誌のyearの年のmonthの月に持つ情報の取得 |


### GET /magazine/list

Mandalaが保持している、雑誌情報（発行タイミングによる分類、情報の年月、雑誌名、最新情報）のリストを返却します。

#### Request Body

なし

#### Response Body

以下のデータの配列をJSONで返却します。  
データが無い場合は空のJSONデータ（[]）を返却します。

| JSON Key     | 型     | サイズ | 必須 | 説明      | Sample    |
| :----------- | :----- | :--- | :-- | :-------- | :-------- |
| title        | String | 50   | ○   | 雑誌のタイトル名 | 週刊少年ジャンプ |
| title_path   | String | 20   | ○   | 雑誌データの検索用パス名 | jump |
| kind         | String | 3    | ○   | 雑誌の種類（不明、冊子、WEBのいずれか） | 冊子 |
| lastest_data | String | 7    | ○   | 保持している最新データの年月 | 2016-09 |

