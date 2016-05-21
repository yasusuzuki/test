package ocjp_silver8;
public class ClassCastTest {
	class A {
		
	}
	class B extends A {
		
	}
	class C extends A {
		
	}
	public void test(){
		A a = new A();
		B b = (B)a;  //A型の変数aはB型の親クラスなのでコンパイルエラーでは気づかない
		
		b = new B();
		//C c = (C)b;  //B型の変数bはそもそもC型とはなんの縁もないのでコンパイルエラー
	}

}
