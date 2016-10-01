# mandala-master-api

漫画に関するデータを返却するAPIサーバです。


## サーバーシステム要件

* Java 8+
* フレームワーク: Play Framework 2.5.8
* 言語: Scala 2.11+


## インストール

* MySQL 5.7
* mandala データベース作成
* DDL登録 [Mandala DDL](https://github.com/manga-data-library/mandala-db-migration/tree/master/DDL)
* マスターテーブルのインサート
* このディレクトリでactivatorを起動しPlayフレームワークをインストール


## 起動方法

```
shell> activator
activator shell> run 80
```


## V1 API リファレンス

- [APIリファレンス](docs/api-reference.md)



## ライセンス

Apache 2 license

### sora-framework-serverからの変更点

本プログラムはsora-framework-serverから以下の変更を行っています。

- 全エンドポイントの変更
- 対象データをアニメから漫画に変更
