# GET /magazine/:name/latest

nameで指定した雑誌について、Mandalaで保持している最新の漫画情報を返却する。


# 機能設計

## 処理フロー

1. リクエスト受信（GET /magazine/:name/latest）
2. Mandalaが保持しているnameの雑誌のデータをDB検索で取得
3. 雑誌データに紐付く最新の漫画データをDB検索で取得
3. レスポンス送信（漫画データ一覧のJSON）

## 雑誌一覧の取得処理

### 1. DBからデータを取得する

```sql
-- 最新の雑誌情報を特定
select m.id as magazine_id, max(concat(lpad(year, 4, '0'), '-', lpad(month, 2, '0'))) as latest_data
from magazine m inner join retension_info ri on m.id = ri.magazine_id
where m.title_path = :name
group by m.title_path;
```

```sql
-- 最新の雑誌情報に紐付く漫画情報を取得
select id, title, title_short, author, author_id, original_author, support_author, public_url, twitter_account, twitter_hash_tag, target_sex, release_status, created_at, updated_at
from manga
where magazine_id = :magazine_id
and start_month <= :latest_data
and (end_month is null or end_month >= :latest_data);
```


### 2. 定数値を意味のある文字列に変換する

存在しない値が定義されていた場合、エラーとはせずに「不明」として値を返却する。

#### target_sex

- 0:不明
- 1:男
- 2:女
- 3:両方

#### release_status

- 0:不明
- 1:新連載
- 2:連載中
- 3:連載終了
- 4:読み切り
- 5:休載中



### テストデータ

```sql
insert into manga (title, title_short, author, author_id, original_author, original_author_id, support_author, support_author_id, magazine_id, start_month, end_month, public_url, twitter_account, twitter_hash_tag, target_sex, release_status) values ('ONE PIECE', 'ワンピ', '尾田栄一郎', null, null, null, null, null, 1, '1997-07', null, 'https://one-piece.com/', '@OPcom_info', '#onepiece', '3', '2');
insert into retension_info (magazine_id, year, month) values (1, 2016, 3);
insert into retension_info (magazine_id, year, month) values (1, 2016, 9);
insert into retension_info (magazine_id, year, month) values (1, 2015, 12);
insert into manga (title, title_short, author, author_id, original_author, original_author_id, support_author, support_author_id, magazine_id, start_month, end_month, public_url, twitter_account, twitter_hash_tag, target_sex, release_status) values ('ONE PIECE', 'ワンピ', '尾田栄一郎', null, null, null, null, null, 1, '1997-07', null, 'https://one-piece.com/', '@OPcom_info', '#onepiece', '3', '2');
insert into manga (title, title_short, author, author_id, original_author, original_author_id, support_author, support_author_id, magazine_id, start_month, end_month, public_url, twitter_account, twitter_hash_tag, target_sex, release_status) values ('銀魂', null, '空知英秋', null, null, null, null, null, 1, '2004-01', null, 'http://www.j-gintama.com/', '@gintamamovie', '#gintama', '3', '2');

insert into manga (title, title_short, author, author_id, original_author, original_author_id, support_author, support_author_id, magazine_id, start_month, end_month, public_url, twitter_account, twitter_hash_tag, target_sex, release_status) values ('銀魂', null, '空知英秋', null, null, null, null, null, 1, '2004-01', null, 'http://www.j-gintama.com/', '@gintamamovie', '#gintama', '3', '2');
insert into retension_info (magazine_id, year, month) values (2, 2016, 4);
insert into retension_info (magazine_id, year, month) values (2, 2016, 10);
insert into retension_info (magazine_id, year, month) values (2, 2015, 9);

--チェック用
select id, title, title_short, author, author_id, original_author, support_author, public_url, twitter_account, twitter_hash_tag, target_sex, release_status, created_at, updated_at
from manga
where magazine_id = 1
and start_month <= '2016-10'
and (end_month is null or end_month >= '2016-10');
```


# プログラム設計

## クラス

controllers.Magazine

## メソッド名

latestMangaData

### パラメータ

- name：雑誌を表すパス名
