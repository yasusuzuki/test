package lambda;
import java.util.function.Consumer;

public class LambdaAbbreviation {
	
	public void doAbbrev(){
		StringBuilder inner_x = new StringBuilder("aa");
		//匿名クラス
		Consumer<String> cons = new Consumer<String>(){
			public void accept(String s){
				System.out.println(s + inner_x);
			}
		};
		inner_x.append("bb");
			
		//ラムダ 最も冗長な書き方
		Consumer<String> cons1 = (String s) -> {
			System.out.println(s + inner_x);
		};
		
		//ラムダ 型推論を使った引数の型省略
		Consumer<String> cons2 = (s) -> {
			System.out.println(s + inner_x);
		};
		
		//ラムダ 引数パラメータの括弧を省略
		Consumer<String> cons3 = s -> {
			System.out.println(s + inner_x);
		};
		
		//ラムダ 本体部分の波括弧を省略
		Consumer<String> cons4 = s -> System.out.println(s + inner_x);
		
		cons.accept("cons");
		cons1.accept("cons1");
		cons2.accept("cons2");
		inner_x.append("cc");

		cons3.accept("cons3");
		cons4.accept("cons4");
	}


	public static void main(String[] args) {
		LambdaAbbreviation app = new LambdaAbbreviation();
		app.doAbbrev();
	
	}
}
