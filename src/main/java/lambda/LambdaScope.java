package lambda;

public class LambdaScope {
	private StringBuilder outerClassField = new StringBuilder("MainString");
	
	public Runnable doLexicalLambda(){
		StringBuilder outerMethodLocal = new StringBuilder("MainInner");
		Runnable r = () -> {
			System.out.println("thisが表すもの : " + this);
			System.out.println("外側のインスタンス変数 : " + outerClassField);
			System.out.println("外側のメソッドローカル変数 : " + outerMethodLocal);
			outerClassField.append("+AddedByLambda");
			outerMethodLocal.append("+AddedByLambda");

		};

		return r;
	}
	public Runnable doLexicalAnonymous(){
		StringBuilder outerMethodLocal = new StringBuilder("MainInner");

		Runnable r = new Runnable(){
			public void run() {
				System.out.println("thisが表すもの : " + this);
				System.out.println("外側のインスタンス変数 : " + outerClassField);
				System.out.println("外側のメソッドローカル変数 : " + outerMethodLocal);
				outerClassField.append("+AddedByAnonymousClass");
				outerMethodLocal.append("+AddedByAnonymousClass");

			}
		};
		return r;
	}
	
	public static void main(String[] args) {
		LambdaScope app = new LambdaScope();
		System.out.println("1st doLexical");
		Runnable r = app.doLexicalLambda();
		r.run();
		r.run();
		System.out.println();
		
		System.out.println("2nd doLexical");
		Runnable ra = app.doLexicalAnonymous();
		ra.run();
		ra.run();
	}
}
