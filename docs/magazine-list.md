# GET /magazine/list

Mandalaが保持している、雑誌情報（発行タイミングによる分類、情報の年月、雑誌名、最新情報）のリストを返却する。


# 機能設計

## 処理フロー

1. リクエスト受信（GET /magazine/list）
2. Mandalaが保持している雑誌の一覧データをDB検索で取得
3. レスポンス送信（雑誌データ一覧のJSON）

## 雑誌一覧の取得処理

### 1. DBからデータを取得する

```sql
select title, title_path, kind, max(concat(lpad(year, 4, '0'), '-', lpad(month, 2, '0'))) as latest_data
from magazine m inner join retension_info ri on m.id = ri.magazine_id
group by m.title_path;
```

#### テストデータ

```sql
insert into magazine (id, title, title_path, publisher_id, kind, release_timing, status) values (1, '週刊少年ジャンプ', 'jump', 1, 1, 2, 1);
insert into retension_info (magazine_id, year, month) values (1, 2016, 3);
insert into retension_info (magazine_id, year, month) values (1, 2016, 9);
insert into retension_info (magazine_id, year, month) values (1, 2015, 12);

insert into magazine (id, title, title_path, publisher_id, kind, release_timing, status) values (2, '週刊少年マガジン', 'magazine', 2, 1, 2, 1);
insert into retension_info (magazine_id, year, month) values (2, 2016, 4);
insert into retension_info (magazine_id, year, month) values (2, 2016, 10);
insert into retension_info (magazine_id, year, month) values (2, 2015, 9);
```


### 2. kindを意味のある文字列に変換する

- 0:不明
- 1:冊子
- 2:WEB


# プログラム設計

## クラス

controllers.Magazine

## メソッド名

list
