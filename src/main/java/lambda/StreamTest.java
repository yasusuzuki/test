package lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import lambda.domain.Meisai;
import lambda.domain.Tanpo;
import lambda.domain.Zokusei;

public class StreamTest {
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
		
		//forEach()の例
		tanpo.stream().forEach( i -> a.put(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i) );
		System.out.println(a);
		
		//
		//mapとcollectを利用した形式。なぜキャストが必要かわからない
		HashMap<String,Tanpo> b =     (HashMap<String, Tanpo>) tanpo.stream()
				//.map( i -> new AbstractMap.SimpleEntry<String,Tanpo>(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(),i))
				.collect(Collectors.toMap(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i -> i));
		System.out.println(b);
		
		//Collectors.groupingByを使った形式。なぜキャストが必要？
		HashMap<String,List<Tanpo>> c = (HashMap<String, List<Tanpo>>) tanpo.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo()) );
		System.out.println(c);
		
		//Collectors.groupingByを使った形式。なぜキャストが必要？
		HashMap<String,?> d = (HashMap<String, ?>) meisai.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()) );
		System.out.println(d);

		//generate()を使ったストリームの生成
		ArrayList<String> stream =  (ArrayList<String>) Stream.generate( () -> "aa" )
				.limit(100)
				.collect(Collectors.toList());
		System.out.println(stream);
	}
}
