package lambda;

import lambda.reflectiontarget.Search01;

public class LambdaTest {

	public void doLambda() {
		//Factoryパターンでラムダを作成する
		Runnable lmb = LambdaFactory.createLambda(Search01.class);
		lmb.run();
	}
	
	public static void main(String[] args) {
		LambdaTest app = new LambdaTest();
		app.doLambda();
		
	}
}
