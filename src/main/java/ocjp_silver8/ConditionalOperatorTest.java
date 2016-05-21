package ocjp_silver8;

public class ConditionalOperatorTest {
	public static int checkNum2(int x){
		int c = 
				(x>1000) ? 4 : // 1000より大
				(x>100) ? 3 : // 1000以下
				(x>10) ? 2 : // 100以下
					1; //10以下
		return c;		
	}
	
	public static int checkNum(int x){
		int c = 
				(x<=1000) ?
				(x<=100) ?
				(x<=10) ?
					1 :  // 10以下
						2: // 100以下
							3: // 1000以下
								4; //1000より大
		return c;
	}
	public static void main(String[] args) {
		int a = 100;
		System.out.println( checkNum(9) );
		System.out.println( checkNum(99) );
		System.out.println( checkNum(999) );
		System.out.println( checkNum(9999) );
		
		System.out.println( checkNum2(9) );
		System.out.println( checkNum2(99) );
		System.out.println( checkNum2(999) );
		System.out.println( checkNum2(9999) );

		
	}
}
