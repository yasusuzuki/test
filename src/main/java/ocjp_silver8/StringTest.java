package ocjp_silver8;

public class StringTest {
	public static void trimTest(){
		//String.trim()は両端の空白を取り除く
		String a = "  Hello  World";
		System.out.println("トリム[" +a.trim() + "]");
		
		//ただし、StringはImmutableなクラスでtrim()によって自身の中身は変わらない
		System.out.println("トリム後[" + a + "]");
		
		//ブランクをトリムしてもNULLになったりせず、ブランクのまま
		String b = "";
		System.out.println("ブランクをトリム[" + b.trim() + "]");
		
	}
	
	public static void main(String[] args) {
		System.out.println("String.trim() のテスト");
		StringTest.trimTest();
	}
}
