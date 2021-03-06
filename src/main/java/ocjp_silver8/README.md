# Oracle Java SE8 Silverの試験勉強で学んだこと
## Javaの用語で混同しやすいもの
 1. メンバと、フィールドとメソッド (8.2. Class Members)
    * メンバは、クラスの構成要素で、クラス内のフィールドとメソッドの総称 (Java8Silver黒本p.181)
    * クラス内で振る舞いを定義するものがメソッド
    * クラス内で定義された変数がフィールド（メソッド内で定義された変数はフィールドと呼ばない）
 1. プリミティブ型と参照型 (4.12. Variables)
    * boolean,int,short,long,float,double,byte,charで宣言された型はすべてプリミティブ型
    * 参照型は、簡単にいえば、プリミティブ型でないもの全部
 1. 変数と型 (4.12.2. Variables of Reference Type)
    * プリミティブ型の変数には、値が入っている
    * 参照型の変数には、インスタンス(＝値)への参照かnullが入っている
    * クラス内で定義される変数はフィールドと呼ぶが、値を入れる箱やメモリ上のある領域を意図したい場合は変数と呼ぶ
 1. 変数の種類　　(4.12.3. Kinds of Variables)
    * staticのついたフィールドは、クラス変数
    * staticのついていないフィールドは、インスタンス変数
    * ifやforなどとともに使われる波括弧のブロック内で定義された変数はローカル変数
    * メソッドの中で定義された変数もローカル変数
 1. 変数の型とインスタンスの型
    *  List a = new ArrayList();
    *  Object o = a
    * oは代入先変数、aは代入元変数、aが参照するのはインスタンス
    * 代入先変数の型はObject、代入元変数の型はList、代入元変数が参照するインスタンスの型はArrayList
 1. 抽象メソッドと具象メソッド
    * 抽象クラス内のabstactが設定されたメソッドのみが抽象メソッド
    * インタフェース内はすべて実装
 1. 継承の際、インタフェースはクラスで実現(implement)する。スーパークラスはサブクラスで継承(extends)する。
 2. アップキャストとダウンキャスト（Java言語規定では、正確には、拡大変換と縮小変換と呼ぶ）
    * サブクラスのインスタンスをスーパークラス型の変数に代入することをアップキャスト。キャスト式は不要
    * スーパークラス型の変数に代入されたインスタンスをもとのサブクラス型の変数に代入することをダウンキャスト。キャスト式が必要
    * キャスト式を使わないと代入できないケースもあるが、プリミティブ型のintとlong、floatとdoubleの間はアップキャストやダウンキャストと呼ばない。
 1. 型変換とキャスト
    * 参照型のスーパークラスやサブクラスの変換はキャストとよぶ。互換性がなければ、ClassCastExceptionが発生するが代入ができれば情報が落ちることはない。
    * プリミティブ型のintとlong,floatとdoubleの変換を型変換よぶ。場合によっては、ビット落ちといって情報が欠けることがあっても、実行時例外(ClassCastExceptionなど)が発生することはない。
 1. 同一性と同値性
    * ==で確認できるのは同一性。equals()で確認するのは同値性(ただし、実装次第)

## Javaの概念の基礎
1. カプセル化： アクセス修飾子
   * ソフトウェアを分割する際に、関係するものを１つにまとめ、無関係なものや関係の薄いものを排除することが重要。関係するフィールドとそれを扱うメソッドが１つにまとめられて、関係ないメソッドからフィールドにアクセスできていない状態がよい設計で、保守性に優れている。
1. ポリモーフィズム：継承、インタフェース、オーバーライド、オーバーロード、
1. Javaの例外処理の特徴：１．エラー処理が通常のプログラム機能と分離されているのでプログラムの構造が改善されｒ。２．例外を処理する場所をプログラマーが選択できる。３．作成される特定のプログラムにあわせて新しい例外を作成できる
## 設計書とかで統一したほうがいいと思った用語
 1. FOR文を回すときは”繰り返す”
 2. RETURNは”返す”、または”戻す”
 3. 代入は設定する
 4. フィールド、メンバ、などと呼ぶが、”変数”で統一する

## Javaの忘れてしまいそうなルール
### アクセス修飾子
1. アクセス修飾子は厳しい順に（公開できる範囲が狭い順に）、private > (default) > protected > public
   * privateはpublicより、〝厳しい”アクセス修飾子
   * publicはprivateより、〝緩い”アクセス修飾子
   * デフォルト修飾子は、パッケージアクセスを持つ修飾子
2. メソッドのオーバーライドでは、スーパークラスのメソッドのアクセス修飾子よりもサブクラスのアクセス修飾子が厳しくなってはいけない（狭くなってはいけない）

### プリミティブのキャスト
   * Java8Silver黒本 仕上げ①-50, 仕上げ①-33, 3章-3, 仕上げ①-59
```
short s = 1000;     // intリテラルからshort変数への代入は値がおさまればO.K 
int   s = 1000L;    // longリテラルからint変数への代入はコンパイルエラーになる

int   t = 1000;     
short u = (short) t; // int型変数からshort型変数への代入(大から小へ)は明示的キャストが必要
```

### オーバーロード
1. オーバーロードは、
   * メソッド名が同じでなければならない
   * 引数だけがどこか(順番、数、型)違っていなければならない
   * 戻り値は同じでも異なってもよい
   * アクセス修飾子は同じでも異なってもよい
   * Java8Silver黒本 6章-9, 6章-10, 6章-11
   * メソッドの名前が一緒で、引数が異なるものだけがオーバーロードとなる。戻り値やアクセス修飾子のみが異なるだけではオーバーロードとなりえず、メソッド定義の重複エラーとなる
   * 互換性のある型でオーバーロードした場合の動きは以下。

```
//ケース１
void doSomething(Integer t){};
void doSomething(int t){};
void doSomething(short s){};
...
doSomething(100);     //明示的型キャストやオートボクシングはオーバーロードでは優先度が下がる。
                      //このケースではint型引数をとる2番目のメソッドが呼ばれる。

//ケース２
void doSomething(float f){};
void doSomething(doouble d){};
...
doSomething(123.45);   //明示的型キャストはオーバーロードの優先度が下がる(Java8Silver黒本-仕上げ①-50)
                       //このケースでは2番目のメソッドが呼ばれる

//ケース３
void doSomething(int i, double d){};
void doSomething(double d, int i){};
...
doSomething(100, 200);   // 引数の型が異なる場合でも、その引数に互換性がある場合
                       // (例えばintとdoubleで引数の型を宣言し、int型を実引数で渡すとき)は、
                       //「あいまいな呼び出し」でコンパイルエラーになる。(Java8Silver黒本-6章-10)

//ケース４
void doSomething(int i, int d){};
void doSomething(double d, double i){};
...
doSomething(100, 200);   // ケース３に似ているが、これは正常に１番目のメソッドが呼ばれる。
                         // intとdoubleには優先度があるようだ 


//ケース５
Object.equals(Object obj){};
SubClass.equals(SubClass obj){};  //引数が異なるのでオーバーライドではなくオーバーロード
...
Object obj = new SubClass();
SubClass sub = new SubClass()
sub.equals(obj);   // オーバーライドされたメソッドから選ぶ際の基準は、実引数の変数型であり参照しているインスタンスの型ではない。そのため、実引数の変数の型がObjectであるので、１番目のメソッドが呼ばれる。

```


## メモ
1. 配列の初期化
```
int[] array = new int[]{1,2,3,4};
int[] array = {1,2,3,4};
```
1. リストの初期化
  * http://qiita.com/yuki2006/items/be1433c50002cc24603b
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
