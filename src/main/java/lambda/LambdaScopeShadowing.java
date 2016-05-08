package lambda;
import java.util.function.IntUnaryOperator;
/**
 * 
 * ラムダ式とスコープ(特に名前空間)について確認するサンプルコードです。
 * ラムダは匿名クラスと違って、自分の名前空間を持たず、そのスコープは定義された箇所と同じです。
 * Enclosing Classで定義されている変数を参照するのは問題ないですが、
 * Enclosing Classで定義されている変数名と同じ名前の変数を宣言するときに、名前が衝突してエラーになることがあります。
 * 下の、run()の中で、次の２つの違いが現れるのは、ラムダに限らず、
 *  - outer_intをラムダの引数名としても問題ない
 *  - param_intやlocal_intをラムダの引数名として再使用しようとするとコンパイルエラーになる
 * 言語仕様はこちらを参照してください
 * http://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html
 * 
 * @author yasu
 *
 */
public class LambdaScopeShadowing {
	//クラス内で定義された変数
	private int outer_int = 100;
		
	private void run(int param_int /*メソッドの引数*/ ){
		int local_int = 100;  //メソッド内で定義された変数
		
		//ここでouter_intを引数の名前に使っても問題ない
		//Enclosing Classのフィールドをshadowするから。言語仕様の$6.4を参照
		IntUnaryOperator lmd = outer_int -> outer_int * 4;
		int ret = lmd.applyAsInt(2);
		System.out.println(ret);
		
		//下はコンパイルエラーになる。言語仕様の$6.4を参照
		//ラムダの引数や本体は、run()メソッドと同じ名前空間にいるから。
		//IntUnaryOperator lmd = param_int -> param_int * 4;
		//IntUnaryOperator lmd = local_int -> local_int * 4;
		
		//しかし、匿名クラスは自分の名前空間を持つのでparam_intやlocal_intを
		//パラメータに使用しても問題ない。
		//匿名クラスはrun()メソッドと異なる名前空間を持っているから。
		IntUnaryOperator ann = new IntUnaryOperator(){
			@Override
			public int applyAsInt(int param_int) {
				return param_int * 4;
			}
		};
		System.out.println(ann.applyAsInt(20));
	}
	
	public static void main(String[] args) {
		new LambdaScopeShadowing().run(100);
	}

}
