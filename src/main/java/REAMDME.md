# Java用語の定義とシステム開発で標準化したい日本語をあげます。
## Java
 1. オブジェクト参照変数とプリミティブ変数
 1. インスタンス・フィールドとクラス・フィールド
 1. メソッドとフィールド
 1. プリミティブ型の変数とオブジェクト型の変数
    * プリミティブ型はint, boolean, floatなど
    * オブジェクト型はObjectのサブクラス
 1. フィールドと変数
    * クラス内で定義される変数は、フィールド
    * メソッド内で定義される変数は、ローカル変数
    * クラス内で定義される変数でも、値を入れる箱やメモリ上の指定した領域を意図したい場合は変数と言ってもよい
 1. 抽象メソッドと具象メソッド
    * 抽象クラス内のabstactが設定されたメソッドのみが抽象メソッド
    * インタフェース内はすべて実装
 1. 継承の際、インタフェースはクラスで実現(implement)する。スーパークラスはサブクラスで継承(extends)する。
 2. アップキャストとダウンキャスト
    * サブクラスのインスタンスをスーパークラス型の変数に代入することをアップキャスト。キャスト式は不要
    * スーパークラス型の変数に代入されたインスタンスをもとのサブクラス型の変数に代入することをダウンキャスト。キャスト式が必要
    * キャスト式を使わないと代入できないケースもあるが、プリミティブ型のintとlong、floatとdoubleの間はアップキャストやダウンキャストと呼ばない。
 1. 型変換とキャスト
    * オブジェクト型のスーパークラスやサブクラスの変換はキャストとよぶ。互換性がなければ、ClassCastExceptionが発生するが代入ができれば情報が落ちることはない。
    * プリミティブ型のintとlong,floatとdoubleの変換を型変換よぶ。場合によっては、ビット落ちといって情報が欠ける場合もあるし、ClassCastExceptionが発生する場合がある。
 1. 同一性と同値性
    * ==で確認できるのは同一性。equals()で確認するのは同値性(ただし、実装次第)

## 内部設計書編
 1. FOR文を回すときは”繰り返す”
 2. RETURNは”返す”、または”戻す”
 3. 代入は設定する
 4. フィールド、メンバ、などと呼ぶが、”変数”で統一する

## Javaの忘れてしまいそうなルール
1. アクセス修飾子は厳しい順に（公開できる範囲が狭い順に）、private > (default) > protected > public
   * privateはpublicより、〝厳しい”アクセス修飾子
   * publicはprivateより、〝緩い”アクセス修飾子
   * デフォルト修飾子は、パッケージアクセスを持つ修飾子
2. メソッドのオーバーライドでは、スーパークラスのメソッドのアクセス修飾子よりもサブクラスのアクセス修飾子が厳しくなってはいけない（狭くなってはいけない）

## Javaの概念の基礎
1. ポリモーフィズム：継承、インタフェース、オーバーライド、オーバーロード、


## メモ
1. 配列の初期化
```
int[] array = new int[]{1,2,3,4};
int[] array = {1,2,3,4};
```
1. リストの初期化
```
// Arrays.asList()を使う。オブジェクト型の配列しか対応していないので、入力はint[]ではなくInteger[]
Integer array[] = new Integer[]{1,2,3,4};
List<Integer> asList = Arrays.asList(array);

// Arrays.asList() を使う。Arrays.asList()は可変長引数をパラメータとしているので、そのまま羅列すればオートボクシングでnew Integer[]{1,2,3,4}を渡すことと同じになる。
// ただしArrays.asList()は、固定長のListを生成するのでadd()やremove()を使うとエラーになるので注意。
List<Integer> asList = Arrays.asList(1,2,3,4);

//Apache Commonsを使うと、プリミティブ型の配列をラッパークラス型の配列に変換してくれる
//その後、Arrays.asList()を使えば、プリミティブ型の配列をラッパークラス型のリストに簡単に変換できる
import org.apache.commons.lang.ArrayUtils;
int array[] = new int[]{1,2,3,4};
Integer objArray[] = ArrayUtils.toObject(array);
List<Integer> asList = Arrays.asList(objArray);

//インスタンスイニシャライザを使う。リストならArrays.asList()が簡潔に書けてよいが、マップの初期化はこれがいいかも。
List<String> list = new ArrayList<String>(){
    {
        add("AA");
        add("BB");
        add("CC");
    }
};

//Collections.addAll()を使う。どちらかというリストに追加したい場合
List<String> list = new ArrayList<String>();
Collections.addAll(list, "AA", "BB", "CC");

```




















