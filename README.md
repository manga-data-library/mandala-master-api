# mandala-master-api

漫画に関するデータを返却するAPI。

## 説明

アニメ作品の情報を返すREST形式のAPIサーバーです。


### サーバーシステム要件

* Java7+
* フレームワーク: Play Framework 2.3+
* 言語: Scala 2.11+


### インストール

* MySQL or MariaDB インストール
* mandala データベース作成
* DDL登録 [Mandala DDL](https://github.com/manga-data-library/mandala-db-migration/tree/master/DDL)
* マスターテーブルのインサート
* このディレクトリでactivatorを起動しPlayフレームワークをインストール


### 起動方法

```
shell> activator
activator shell> start 80
```

### ライセンス

Apache 2 license

#### 変更点

本プログラムはsora-framework-serverから以下の変更を行っています。

- 全エンドポイントの変更
- 対象データをアニメから漫画に変更


## V1 API リファレンス

### URI情報

| URI情報    | 値     |
| :------------- | :------------- |
| Host         | xxxxxxxxx（未定） |
| Protocol     | HTTP  |
| Base URI     | http://xxxxxxxxx/api/master/v1 |


### アクセスURI一覧

| HTTP method | URI             | 用途                   |
| :---------- | :-------------- | :------------- |
| GET         | /magazine/list      | 雑誌一覧の取得 |
| GET         | /magazine/:release/:name      | 雑誌一覧の取得 |
| GET         | /magazine/:release/:name/:year      | 雑誌一覧の取得 |
| GET         | /magazine/:release/:name/:year/:month | 雑誌一覧の取得 |



### GET /magazine/list

Mandalaが保持している、雑誌情報（発行タイミングによる分類、情報の年月、雑誌名、最新情報）のリストを返却します。

#### Request Body

なし

#### Response Body

| Property     | Type               |description|Sample|
| :------------ | :------------------ |:------|:-------|
