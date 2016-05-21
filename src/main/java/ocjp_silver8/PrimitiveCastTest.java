package ocjp_silver8;



public class PrimitiveCastTest {
	public static void show(int i)   {System.out.println("int    " + i);}
	public static void show(long i)  {System.out.println("long   " + i);}
	public static void show(short i) {System.out.println("short  " + i);}
	public static void show(float i) {System.out.println("float  " + i);}
	public static void show(double i){System.out.println("double " + i);}
	
	public static void testInteger(){
		//long > int > short
		//大きい値を小さい型に代入するときはキャスト式が必要
		final int SAMPLE_INT = 1_000_000_000;
		final long SAMPLE_LONG = 1_000_000_000_000L;
		final short SAMPLE_SHORT = 10_000;
		int i=0; long l=0; short s=0;
		i = (int)SAMPLE_LONG; show(i);  //ビット落ち！！
		i = SAMPLE_SHORT; show(i); 
		
		l = SAMPLE_INT; show(l);
		l = SAMPLE_SHORT; show(l);
		
		s = (short)SAMPLE_LONG; show(s);//ビット落ち！！  
		s = (short)SAMPLE_INT; show(s); //ビット落ち！！ 
	}
	public static void testFloat(){
		final float SAMPLE_FLOAT = 1000.123F;
		final double SAMPLE_DOUBLE = 5432.124;
		float f = 0;double d = 0;
		
		f = (float)SAMPLE_FLOAT; show(f);
		d = SAMPLE_DOUBLE; show(d);
		
		
		
	}
	public static void main(String[] args) {
		PrimitiveCastTest.testInteger();
		PrimitiveCastTest.testFloat();

	}
}
