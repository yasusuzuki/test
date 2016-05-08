package lambda;
/**
 * 関数型インタフェースのサンプルコード
 * 
 * Consumer,Supplier,Function,PredictなどのJDK付属の
 * 関数型インタフェースになければ自作する必要がある。
 * @author yasu
 *
 */
public class LambdaInterface {
	
	@FunctionalInterface
	private interface Ex {
		public void doLambda();
	}
	
	public void runLambda(){
		Ex ex = () -> System.out.println("AA");
		ex.doLambda();
	}
	
	public static void main(String[] args) {
		new LambdaInterface().runLambda();
	}
}
