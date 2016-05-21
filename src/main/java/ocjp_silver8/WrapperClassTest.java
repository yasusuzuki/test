package ocjp_silver8;

public class WrapperClassTest {
	public static void main(String[] args) {
		String str = "10";
		String str2 = "TRUE";
		//parseXXXは文字列からプリミティブ型に変換する
		Integer a = Integer.parseInt(str);
		Boolean b = Boolean.parseBoolean(str2);
		//valueOfは文字列からラッパークラスのインスタンスを生成する
		Integer c = Integer.valueOf(str);
		Boolean d = Boolean.valueOf(str2);
		System.out.println(a + "," + b);
		System.out.println(c + "," + d);
	}
}
