package ocjp_silver8;

public class LiteralTest {
	
	
	public static void main(String[] args) {
		short s = 1_000_0; //IntリテラルからShort変数は、値の範囲ならOK
		
		
		int i = 1000;
		short u = (short)i;    //int変数からshort変数へは、明示的なキャストが必要。
		System.out.println(u); //値の範囲外でもエラーではないが、ビット落ちし、値が変な値になる
		
		
		int w = (int)(2L * 3);   //longリテラルからint変数は、キャスト無しではエラー
		long l = 1000;
		i = (int)l;          //long変数からint変数は、キャスト無しではエラー
	}
}
