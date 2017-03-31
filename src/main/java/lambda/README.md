# ラムダのテストです
## ソースコード
 1. LambdaAbbreviation.java
   * ラムダの省略記法のテストです
 2. LambdaInterface.java
   * 関数型インタフェースを自作する
 3. LambdaScope.java
   *  ラムダからアクセスできるラムダの外側の変数は、ラムダの状態として紐づけられている
 4. LambdaScopeShadowing.java
   * ラムダは匿名クラスと違って、自分の名前空間を持たない。


## ラムダとは
   * 簡潔に書ける匿名クラスとメソッド
   * http://www.lambdafaq.org/what-is-a-lambda-expression/

## ラムダとは　ラムダが導入された理由を探る
   * 関数型プログラミング言語でラムダは評価が高かった。Javaではいままで匿名クラスがあったがそれだと書き方が冗長。
   * Javaプログラマは、冗長な書き方に我慢強い。冗長な匿名クラスで我慢していたのになぜ今さら簡潔な書き方が必要か？（答）ストリームAPI。
   * ストリームAPIがなぜ必要か？(答)マルチコアのコンピュータで並列処理をさせる必要があり、並列処理をするために複雑化するFORループの実装隠ぺいしたかったから。
   * http://www.lambdafaq.org/why-are-lambda-expressions-being-added-to-java/
   * Javaはオブジェクト指向であり、データとコードをセットにした単位を扱うのが原則、コード単位で扱うのは原則に反する。メソッドへの参照を持つことができない。
   * そこで、Javaでコードのみを疑似的に扱うために、抽象メソッドを一つだけ持つインタフェースの実装に特別な意味を持たせるようにJavaの言語仕様を拡張した
   * Javaのラムダは、Javaの匿名クラスのオブジェクトの一種として扱われ、その原理・原則に従いつつ、”メソッド１つだけ持つインタフェースの実装”を完結に表現する方法を提供する
   
## ラムダに近いもの
  * 匿名クラス
    * 匿名クラスに長い実装は普通書かない。書くとするならそれは名前付きのクラスに昇格すべき
    * 匿名クラスに短い実装を書くのが普通。
  
## ラムダの使いどころ
  * 匿名クラスの使いどころとおなじ。そもそもラムダでできることは匿名クラスでもできる
    * RunnableやFutureなど、マルチスレッド化・非同期化
  * ストリームAPI
    * ラムダは匿名クラスよりも簡潔に書けるので、1行だけの簡単なコードも躊躇なくオブジェクトとして定義できる 
    * 単純計算もラムダで定義して、ストリームAPIに渡す。ストリームAPIによって並列処理化できる。
    * Internal Iteration(Iteratorパターン)から External Iteration（ストリーム）へ
  * Commandパターン Observerパターン Strategyパターン Template MethodパターンとFactoryメソッドの組み合わせ
    * 今まで匿名クラスを使って実装されることがあったが、ちょっとしたCommandやStrategyはラムダで簡潔に書けることで、それらパターンの使用が気軽なものになる。
  * Callbackイディオム --- 細部のロジックだけ実装し、それをどう呼び出すかはフレームワークに任せる


## Link
  * まとめがよい
    * http://www.ne.jp/asahi/hishidama/home/tech/java/functionalinterface.html
    * http://d.hatena.ne.jp/nowokay/20130824
    * http://qiita.com/tasogarei/items/60b5e55d8f42732686c6
  * こちらもわかりやすい。型推論の説明が良い。ページ下部の「ステップバイステップで学ぶラムダ式・ストリームAPI」はわかりやすい
    * http://masatoshitada.hatenadiary.jp/entry/2015/02/09/190150
  * ラムダが導入されることと、デザインパターンとの関係を説明している：Commandパターンなどを言語的にサポートできるようになった
    * https://codezine.jp/article/detail/8300
  * Lambda FAQ - Maurice NaftalinというフリーランスのJavaアーキテクトが手掛けるFAQ
    * http://www.lambdafaq.org/
  * State of the Lambda - Brian GoetzによるJavaのラムダ式の草案
    * (原文) http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-final.html
    * (翻訳) http://bitterfox.web.fc2.com/java/jsr/jsr335/lambda-state-4-JP.html
    * (2012年11月 旧版) http://cr.openjdk.java.net/~briangoetz/lambda/sotc3.html
  * Oracleのチュートリアル　
    * http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    * http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
  * Java8 言語仕様 ラムダの部分
    * http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27
  * Stack Over Flow - What are the advantages of Lambda Expressions for multicore systems?
    * http://stackoverflow.com/questions/16981796/what-are-the-advantages-of-lambda-expressions-for-multicore-systems
  * Oracleの記事　Java8のリリース前の記事なので正確でない可能性があるが、ラムダ導入の目的やいきさつがわかる
    * http://www.oracle.com/technetwork/jp/articles/java/architect-lambdas-part1-2080972-ja.html
    * http://www.oracle.com/technetwork/jp/articles/java/architect-lambdas-part2-2081439-ja.html
  * ラムダとinvokedynamicやメソッドハンドルの関係を説明している
    * http://www.infoq.com/jp/articles/Java-7-Features-Which-Enable-Java-8
    
## 関数型プログラミングとは何かを考える
  * 関数型プログラミング = コードブロックをオブジェクトとみなす
  * オブジェクト指向プログラミング = コードとデータをセットでオブジェクトとみなす
  * 関数は、入力が定まると出力が定まるもの。オブジェクトは、状態と入力が決まると出力が定まるもの
  * ラムダに与える入力が一意に決まると出力が決まるか？NO.Javaのラムダはクロージャの性質を持っていて、ラムダを定義したメソッドやクラスが持つ変数の状態をひきずっている。
  * Javaではラムダとクロージャは同じ意味で使われる。ただし、厳密にはラムダは「名前を持たない関数」という意味を持ち、クロージャは「関数の外側で定義された変数の状態をもつ」という意味を持つ。
    * http://www.lambdafaq.org/lambdas-and-closures-whats-the-difference/

## Javaでラムダ式を成立させる要素：　省略書式、関数型インタフェース、型推論、クロージャ
  * 省略書式
    * (型 ラムダ引数) -> {ラムダ本体;};
    * (ラムダ引数) -> {ラムダ本体;};
    * ラムダ引数 -> {ラムダ本体;};
    * ラムダ引数 -> ラムダ本体;
  * 関数型インタフェース
    * 定義されている抽象メソッドが１つだけあるインタフェース
    * staticメソッドやデフォルトメソッド、Objectクラスにあるpublicメソッドは定義されていてもオッケー
  * 型推論
    * ラムダの代入文の左辺の型から推測することで、右辺の記述（クラス名、引数型名、リターンコード）を削減することに成功している
    * ラムダの代入文なら左辺を見れば目で終えるが、ラムダをメソッドの引数として渡すときは、そのメソッドの定義までたどる必要がある。
    * ストリームAPIで何段ものメソッドチェーンを組み立てると、推論の根拠がどこにあるのか目で追えなくなる。ここがラムダが可読性に問題があるといわれる理由の一つ。
    * ラムダ式は何クラスのインスタンスとして扱われるか？　（答）定義したときの左辺の型によって異なり、コンパイラが決める
    * http://masatoshitada.hatenadiary.jp/entry/2015/02/09/190150
    * ただし、この型推論はコンパイラが実施するもの。VMが動的に実施しているものではないので、ソースコードを見れば人間でも一意に判断できるもの。＜－難しくない。
  * クロージャ
    * ラムダは、外側で定義された変数を、状態としてもつ。その変数をCaptured Variableという。下のページのセクション７を参照。
    * http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-final.html
    
## ラムダと匿名クラスの機能的な違い
  * 以下以外、機能的に、違いはない
  * this が表すもの
    * 匿名クラスは匿名クラスを表す。ラムダは、ラムダ定義を含むクラスを表す
    * 匿名クラスはインスタンス化するたび違うTHIS。ラムダは同じTHIS。
  * 匿名クラス内の変数が使えない
    * 匿名クラスは、あくまでクラス。基底クラス内に変数を持つことができ、匿名クラスはそれを継承して状態を持てる
    * ラムダは、疑似的に関数を表す。関数型インタフェース内に変数を持ち、状態を持つことはできない
  * http://www.bbreak.co.jp/technique/doc/java8/JavaSE8_Lambda.pdf

  
## 既に用意されている代表的な関数型インタフェース
  * Supplier.get() --- 引数無しで実行し、VOID以外の戻り値を返す
  * Consumer.accept() --- 引数を１つとり実行するが戻り値はVOID。VBAでいうサブルーチン
  * Predicate.test() --- 引数を１つとり、boolean型の戻り値を返す。チェックに使う。
  * Function.apply() --- 引数を１つとり実行し、VOID以外の戻り値を返す。VBAのFunction
  * Runnable.run() --- 引数無しで実行し、戻り値はVoid。Threadを実装するためのものだが、関数型インタフェースの条件を満たしている。
  * Callable.call() --- 引数無しで実行し、戻り値はVoid以外。並列処理を実装するためのものだが、関数型インタフェースの条件を満たしている。
  * http://www.ne.jp/asahi/hishidama/home/tech/java/functionalinterface.html

## メソッド参照
  * 既に定義されているメソッドをラムダとして切り出してラムダとして使える
  * http://www.ne.jp/asahi/hishidama/home/tech/java/methodreference.html
  * http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html

## ストリーム
  * 生成操作
    * Stream<String> stream = list.stream(); ( where list = ArrayList<String> )
    * Stream<String> stream = Stream.of("a","b","c");
    * Stream<String> stream = Array.stream(strings); (where string = String[])
    * Stream.generate();  //無限にループするストリームの生成
    * IntStream.range(1,10); //1から10まで繰り返すストリームの生成
  * 中間操作（ストリームを受け取り、別のストリームを作成する）
    * Stream<T> filter(Predicate<T> applies); //ストリームの要素から条件で絞る
    * Stream<R> map(Function<T,R> mapper);  //ストリームの要素を変換する
    * sorted(); flatMap();distinct();limit();skip();keep();
  * 終端操作(ストリームを受け取り、コレクションを作成する)
    * collect(Collectors.toList())
    * collect(Collectors.toMap(マップのキー、マップの値));
    * collect(Collectors.groupingBy(マップのキー));
    * reduce(); // reduce( (total,value) -> total = total+value )として、  ストリームの各要素の総計を求めれる
    * count();
    * max(); min();
    * allMatch(); anyMatch(); noneMatch();
    * findFirst(); findAny();
    
    
