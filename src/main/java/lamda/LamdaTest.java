package lamda;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import lamda.reflectiontarget.Search01;

public class LamdaTest {
	ArrayList<Meisai> meisai = new ArrayList<Meisai>();
	ArrayList<Tanpo> tanpo = new ArrayList<Tanpo>();
	ArrayList<Zokusei> zokusei = new ArrayList<Zokusei>();
	
	@Before
	public void init(){
		Collections.addAll(meisai,
		new Meisai("S01","傷害","01"),
		new Meisai("S01","傷害","02"),
		new Meisai("S01","傷害","03"));

		Collections.addAll(tanpo,
		new Tanpo("S01","傷害","01","死亡"),
		new Tanpo("S01","傷害","02","死亡"),
		new Tanpo("S01","傷害","02","後遺"),
		new Tanpo("S01","傷害","03","死亡"),
		new Tanpo("S01","傷害","03","後遺"));
		
		Collections.addAll(zokusei,
		new Zokusei("S01","傷害","01","死亡","保険料","100"),
		new Zokusei("S01","傷害","02","死亡","保険料","100"),
		new Zokusei("S01","傷害","02","後遺","保険料","100"),
		new Zokusei("S01","傷害","03","死亡","保険料","100"),
		new Zokusei("S01","傷害","03","後遺","保険料","100"));	
	}
	
	@Test
	public void test1(){
		System.out.println(" *** Run test1 ***");
		HashMap<String, Tanpo> a = new HashMap<String, Tanpo>();
		tanpo.stream().forEach( i -> a.put(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i) );
		System.out.println(a);
		
		//mapとcollectを利用した形式。なぜキャストが必要かわからない
		HashMap<String,Tanpo> b =     (HashMap<String, Tanpo>) tanpo.stream()
				//.map( i -> new AbstractMap.SimpleEntry<String,Tanpo>(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(),i))
				.collect(Collectors.toMap(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i -> i));
		System.out.println(b);
		
		//Collectors.groupingByを使った形式。なぜキャストが必要？
		HashMap<String,List<Tanpo>> c = (HashMap<String, List<Tanpo>>) tanpo.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo()) );
		System.out.println(c);
		
		HashMap<String,?> d = (HashMap<String, ?>) meisai.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()) );
		System.out.println(d);

		
		ArrayList<String> stream =  (ArrayList<String>) Stream.generate( () -> "aa" ).limit(100).collect(Collectors.toList());
		System.out.println(stream);
	}
	
	
	@Test
	public void test2() {
		System.out.println(" *** Run test2 ***");

		lamdaScope(100).run();
		lamdaScope(200).run();
		
		LamdaFactory.createLamda(Search01.class).run();
	}
	
	//ラムダが参照する変数のスコープ(変数を使える範囲)は、ラムダを定義したクラスやメソッドと同じになる。
	//
	public Runnable lamdaScope(int round){ //引数もラムダで参照可能
		int trial = round + 1; // ローカル変数もラムダで参照可能
		Runnable runner = () ->{
			//
			tanpo.add(new Tanpo("S01","傷害","01","死亡") ); //親のインスタンス変数も参照可能。クラス型の内部の変更なら可
			System.out.println(round + " " + trial + " " + tanpo);
		};
		return runner;
	}
}
