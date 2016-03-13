package reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
/*
  
TODO: メモリの使用量についての計測がない
TODO: DBやルールへのアクセスに対する比較や、文字列操作以外の演算に対する比較も欲しい
TODO: Java7から導入されたMethodHandleやLamdaを使うと高速に動くらしいがそれを試す

 ============= 現時点の結論 ===============
 －3万回程度のアクセスならリフレクションと直接呼出しの差は2ms程度なので、オンラインアプリなら問題ない。バッチはもう少し検討する必要があるかもしれない。
 　　－　直接呼出しは3万回のゲッターセッターで約1ms程度(ただし、意図しない最適化を防ぐためのswitch分の負荷が混じってしまっている。
　　　　　switch文無しで同じゲッターセッターを3万回アクセスする状況でも1ms程度だったので、switch分による負荷は無視してよいと考える)
　　－　リフレクション呼び出しにいくつか方法があるなかで最も性能が良い方法でも、3ms程度
　　－　つまり、リフレクションは直接呼出しに比べて3倍遅い。ただし、3万回呼び出しで2msの差である。

 －　リフレクションによる参照を使ったメソッドの呼び出しやフィールドへのアクセスするよりも、その参照を取得する負担が1.5~9倍程度かかる
 　　つまり、メソッドやフィールドへの参照をあらかじめ取得してキャッシュしておくと、リフレクションの性能を著しく向上させることができる
 －　リフレクションにおいて、メソッドやフィールドへの参照を取得するのはフィールドの方が速いが、参照を使って実際にメソッドを呼び出したり
 　　フィールドにアクセスするのは メソッドのほうが速い。対照的に、直接呼び出しでは、ゲッターセッターを使わず直接フィールドにアクセスしたほうが速い。、
 　　ただし、その差は3万回アクセスで2ms程度なので、大した差ではない。
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionBenchMark {
	private  final int RUNS = 30000;
	private A[] as = new A[RUNS];
	private B[] bs = new B[RUNS];
	
	private Field[] sourceFields = new Field[RUNS];
	private Field[] destinationFields = new Field[RUNS];
	
	private Method[] getters = new Method[RUNS];
	private Method[] setters = new Method[RUNS];
	
	private MethodHandle[] mh_getters = new MethodHandle[RUNS];
	private MethodHandle[] mh_setters = new MethodHandle[RUNS];
	

    {
    	try{
		for (int i = 0; i < RUNS; i++) {
			//インスタンスはあらかじめ作成しておく
			as[i] = A.class.newInstance();
			bs[i] = B.class.newInstance();
			//Methodへの参照をあらかじめ作成しておく;
			getters[i] = A.class.getMethod("getF" + String.format("%02d", i%30+1),new Class<?>[]{});
			setters[i] = B.class.getMethod("setF" + String.format("%02d", i%30+1),String.class);
			//mh_getters[i] = MethodHandles.publicLookup().findVirtual(A.class,"getF"+ String.format("%02d", i%30+1),MethodType.methodType(String.class));
			//mh_setters[i] = MethodHandles.publicLookup().findVirtual(B.class,"setF"+ String.format("%02d", i%30+1),MethodType.methodType(void.class, String.class));

			sourceFields[i] = A.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			sourceFields[i].setAccessible(true);
			destinationFields[i] = B.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			destinationFields[i].setAccessible(true);

			mh_getters[i] = MethodHandles.lookup().unreflectGetter(sourceFields[i]);
			mh_setters[i] = MethodHandles.lookup().unreflectSetter(destinationFields[i]);
			}
    	}catch(Exception e){
    		e.printStackTrace();
    		System.exit(1);
    	}

    	
    }	
	public static void main(String[] args) throws Exception {
		ReflectionBenchMark ap = new ReflectionBenchMark();
		
		for (int i=1; i <= 50; i++) {
			System.out.printf("*** ROUND %2d ***\n",i);
			//リフレクション無し
			ap.doRegular( i );
			//ゲッターセッターへのMethodのリフレクション使用。ただしMethodのインスタンスは都度作成
			ap.doReflectionMethodWithoutReferenceOptimized( i );
			//Fieldのリフレクション使用。ただしFieldのインスタンスは都度作成			
			ap.doReflectionFieldWithouReferenceOptimized( i );
			//ゲッターセッターへのMethodのリフレクション使用。Methodのインスタンスはあらかじめ作成
			ap.doReflectionMethodWithReferenceOptimized( i );
			//Fieldのリフレクション使用。Fieldのインスタンスはあらかじめ作成
			ap.doReflectionFieldWithReferenceOptimized( i );
			//Fieldのリフレクション使用。Fieldのインスタンスはあらかじめ作成。
			//さらに、文字列連結の負荷とリフレクションの不可を比べるために、任意も文字を毎回連結してからセッターを呼ぶ
			ap.doStringConcatAsBenchMark( i );
			//(想定はしていないが)リフレクションによるインスタンス化の負荷を計測する
			ap.doNewInstanceWithReflectionAsBenchMark(i);
			//System.gc();
			ap.doMethodHandleWithReferenceOptimized(i);
		}


	}

	public void doRegular(int round) throws Exception {
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			//bs[i].setF01( as[i].getF01() ); //Swtich文の負荷分を取得するための命令文

			switch (i%30+1) {
			case 1:
				bs[i].setF01( as[i].getF01() );				
				break;
			case 2:
				bs[i].setF02( as[i].getF02() );				
				break;
			case 3:
				bs[i].setF03( as[i].getF03() );				
				break;
			case 4:
				bs[i].setF04( as[i].getF04() );				
				break;
			case 5:
				bs[i].setF05( as[i].getF05() );				
				break;
			case 6:
				bs[i].setF06( as[i].getF06() );				
				break;
			case 7:
				bs[i].setF07( as[i].getF07() );				
				break;
			case 8:
				bs[i].setF08( as[i].getF08() );				
				break;
			case 9:
				bs[i].setF09( as[i].getF09() );				
				break;
			case 10:
				bs[i].setF10( as[i].getF10() );				
				break;
			case 11:
				bs[i].setF11( as[i].getF11() );				
				break;
			case 12:
				bs[i].setF12( as[i].getF12() );				
				break;
			case 13:
				bs[i].setF13( as[i].getF13() );				
				break;
			case 14:
				bs[i].setF14( as[i].getF14() );				
				break;
			case 15:
				bs[i].setF15( as[i].getF15() );				
				break;
			case 16:
				bs[i].setF16( as[i].getF16() );				
				break;
			case 17:
				bs[i].setF17( as[i].getF17() );				
				break;
			case 18:
				bs[i].setF18( as[i].getF18() );				
				break;
			case 19:
				bs[i].setF19( as[i].getF19() );				
				break;
			case 20:
				bs[i].setF20( as[i].getF20() );				
				break;
			case 21:
				bs[i].setF21( as[i].getF21() );				
				break;
			case 22:
				bs[i].setF22( as[i].getF22() );				
				break;
			case 23:
				bs[i].setF23( as[i].getF23() );				
				break;
			case 24:
				bs[i].setF24( as[i].getF24() );				
				break;
			case 25:
				bs[i].setF25( as[i].getF25() );				
				break;
			case 26:
				bs[i].setF26( as[i].getF26() );				
				break;
			case 27:
				bs[i].setF27( as[i].getF27() );				
				break;
			case 28:
				bs[i].setF28( as[i].getF28() );				
				break;
			case 29:
				bs[i].setF29( as[i].getF29() );				
				break;
			case 30:
				bs[i].setF30( as[i].getF30() );				
				break;
			default:
				System.exit(1);
				break;
			}
		}

		System.out.printf("%2d %-30s\t%,20d ns%n",round,"直接呼出",  (System.nanoTime() - start) );
	}

	public  void doReflectionMethodWithoutReferenceOptimized(int round) throws Exception {
		String[] getterNames = new String[RUNS];
		String[] setterNames = new String[RUNS];
		
		for (int i = 0; i < RUNS; i++) {
			//Method名は作成しておくが、Methodへの参照をあらかじめ作成しておかない
			getterNames[i] = "getF" + String.format("%02d", i%30+1);
			setterNames[i] = "setF" + String.format("%02d", i%30+1);
		}
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			Method getter = A.class.getMethod(getterNames[i], new Class<?>[]{});
			Method setter = B.class.getMethod(setterNames[i], String.class);
			String val = (String) getter.invoke(as[i], new Object[]{});
			setter.invoke(bs[i], val );
		}
		System.out.printf("%2d %-30s\t%,20d ns%n",round,"リフレクションメソッド事前参照取得・無",(System.nanoTime() - start) );
	}

	public  void doReflectionMethodWithReferenceOptimized(int round) throws Exception {

		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			String val = (String) getters[i].invoke(as[i], new Object[]{});
			setters[i].invoke(bs[i], val );
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"リフレクションメソッド事前参照取得・有",(System.nanoTime() - start) );
	}
	
	public  void doReflectionFieldWithouReferenceOptimized(int round) throws Exception {
		Field[] sourceFields = new Field[RUNS];
		Field[] destinationFields = new Field[RUNS];
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			sourceFields[i] = A.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			sourceFields[i].setAccessible(true);
			destinationFields[i] = B.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			destinationFields[i].setAccessible(true);
			
			String val = (String) sourceFields[i].get(as[i]);
			destinationFields[i].set(bs[i], val);
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"リフレクションフィールド事前参照取得・無",(System.nanoTime() - start) );
	}
	
	
	public  void doReflectionFieldWithReferenceOptimized(int round) throws Exception {
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			String val = (String) sourceFields[i].get(as[i]);
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "リフレクションフィールド事前参照取得・有", (System.nanoTime() - start) );
	}
	
	
	public  void doStringConcatAsBenchMark( int round ) throws Exception {
		String[] dummyString = new String[RUNS];
		
		for (int i = 0; i < RUNS; i++) {
			dummyString[i] = String.format("%02d", i%30+1);
		}
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			//文字列の操作に対する、リフレクションのオーバーヘッドを測るために、任意の文字を連結する
			String val = (String) sourceFields[i].get(as[i]) + dummyString[i];
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "リフレクションフィールド事前参照取得・有＋文字連結", (System.nanoTime() - start) );
	}
	
	public  void doNewInstanceWithReflectionAsBenchMark(int round) throws Exception {
		String[] dummyString = new String[RUNS];
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			as[i] = A.class.newInstance();
			bs[i] = B.class.newInstance();
			String val = (String) sourceFields[i].get(as[i]) + dummyString[i];
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "リフレクションフィールド事前参照取得・有＋文字連結＋インスタンス生成", (System.nanoTime() - start) );
	}
	

	

	public  void doMethodHandleWithReferenceOptimized(int round) throws Exception {
       
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			try {
				String val = (String) mh_getters[i].invoke(as[i]);
				mh_setters[i].invoke(bs[i],val);
				//String val = (String) mh_getter.invoke(as[i]);
				//mh_setter.invoke(bs[i],val);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"メソッドハンドル事前参照取得・有",(System.nanoTime() - start) );
	}
	
	
	//MethodHandleが最も最適化・高速化する方法 final。動的に呼び出しを変更することができないので、今回は却下
	private static final MethodHandle mh_getter = createMethodHandle(A.class,"getF01",MethodType.methodType(String.class));
	private static final MethodHandle mh_setter = createMethodHandle(B.class,"setF01",MethodType.methodType(void.class, String.class));
	private static MethodHandle createMethodHandle(Class cls, String methodName, MethodType methodType ){
        try {
            return MethodHandles.lookup().findVirtual( cls, methodName , methodType );
        } catch ( Throwable e ) {
            e.printStackTrace();

            return null;
        }
	}
}
